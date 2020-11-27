package com.enclave.decker.recipeapp.base

import android.os.Bundle
import android.widget.Toast
import com.enclave.decker.recipeapp.BR
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.enclave.decker.recipeapp.base.exceptions.RecipeException
import com.enclave.decker.recipeapp.base.validation.ValidatableField
import com.enclave.decker.recipeapp.base.validation.ValidationProblem
import com.enclave.decker.recipeapp.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import kotlin.reflect.KClass
import com.enclave.decker.recipeapp.R


abstract class BaseBindingActivity<TBinding : ViewDataBinding, TViewModel : ViewModel> :
    BaseActivity() {

    @Inject
    lateinit var factory: ViewModelsFactory

    lateinit var viewModel: TViewModel
    lateinit var binding: TBinding

    protected abstract val layoutId: Int
    protected abstract val viewModelClass: KClass<TViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory)[viewModelClass.java]

        init(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.model, viewModel)
        binding.lifecycleOwner = this

        initView(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)
    abstract fun initView(savedInstanceState: Bundle?)

    fun handleError(error: RecipeException) {
        when (error) {
            is RecipeException.ValidationException -> showValidationError(error.problems)
        }
    }

    protected open fun showValidationError(problems: List<Pair<ValidatableField, ValidationProblem>>) {
        Toast.makeText(this, getString(R.string.error_required_message), Toast.LENGTH_SHORT).show()
    }

    protected inline infix fun <T> LiveData<T>.bindTo(crossinline action: (T?) -> Unit) =
        this.observe(this@BaseBindingActivity, Observer { action(it) })

    protected inline infix fun <T> Observable<T>.bindTo(crossinline action: (T) -> Unit) {
        compositeDisposable += observeOn(AndroidSchedulers.mainThread()).subscribe { action(it) }
    }

    protected inline infix fun Observable<Unit>.bindTo(crossinline action: () -> Unit) {
        compositeDisposable += observeOn(AndroidSchedulers.mainThread()).subscribe { action() }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}