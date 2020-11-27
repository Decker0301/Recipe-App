package com.enclave.decker.recipeapp.ui.recipedetails.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.enclave.decker.recipeapp.base.BaseViewModel
import com.enclave.decker.recipeapp.base.exceptions.RecipeException
import com.enclave.decker.recipeapp.base.validation.ValidatableField
import com.enclave.decker.recipeapp.base.validation.ValidationProblem
import com.enclave.decker.recipeapp.roomdatabase.base.Result
import com.enclave.decker.recipeapp.roomdatabase.base.resultFlatMap
import com.enclave.decker.recipeapp.roomdatabase.usecases.AddRecipe
import com.enclave.decker.recipeapp.roomdatabase.usecases.EditRecipe
import com.enclave.decker.recipeapp.roomdatabase.usecases.GetRecipe
import com.enclave.decker.recipeapp.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val context: Context,
    private var addRecipe: AddRecipe,
    private var getRecipe: EditRecipe
    ) : BaseViewModel() {

    private val addRecipeSuccessSubject = PublishSubject.create<Unit>()
    val addRecipeSuccess: Observable<Unit> = addRecipeSuccessSubject.hide()

    val isExecuting = MutableLiveData<Boolean>()

    val recipeId = MutableLiveData<Int>()

    val title = MutableLiveData<String>()
    val type = MutableLiveData<String>()
    val ingredient = MutableLiveData<String>()
    val step = MutableLiveData<String>()

    fun getRecipe(id: Int?){
        compositeDisposable += getRecipe.execute(id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isExecuting.value = true }
            .doFinally { isExecuting.value = false }
            .subscribe{
                title.value = it.title
                type.value = it.type
                ingredient.value = it.ingredients
                step.value = it.steps
            }
    }

    fun addRecipe() {
        compositeDisposable +=
            getForm().resultFlatMap {
            addRecipe.execute(AddRecipe.RecipeAdd(it.title, it.type, it.ingredient, it.step))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isExecuting.value = true }
            .doFinally { isExecuting.value = false }
            .subscribe(Consumer {
                when (it) {
                    is Result.Success -> addRecipeSuccessSubject.onNext(Unit)
                    is Result.Error -> {
                    }
                }
            })
    }

    private fun getForm(): Single<Result<AddRecipe.RecipeAdd>> =
        Single.fromCallable<Result<AddRecipe.RecipeAdd>> {
            val errors = mutableListOf<Pair<ValidatableField, ValidationProblem>>()

            val title = title.value.orEmpty().also {
                when {
                    it.isBlank() -> ValidatableField.TITLE to ValidationProblem.EMPTY
                    else -> null
                }?.let {
                    errors.add(it)
                }
            }
            val type = type.value.orEmpty().also {
                when {
                    it.isBlank() -> ValidatableField.TITLE to ValidationProblem.EMPTY
                    else -> null
                }?.let {
                    errors.add(it)
                }
            }

            if (errors.isEmpty()) {
                Result.Success(
                    AddRecipe.RecipeAdd(
                        title = title,
                        type = type,
                        ingredient = ingredient.value,
                        step = step.value
                    )
                )
            } else {
                Result.Error(RecipeException.ValidationException(errors))
            }
        }
}