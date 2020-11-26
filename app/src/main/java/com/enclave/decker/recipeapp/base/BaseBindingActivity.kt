package com.enclave.decker.recipeapp.base

import android.os.Bundle
import com.enclave.decker.recipeapp.BR
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.enclave.decker.recipeapp.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import kotlin.reflect.KClass


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