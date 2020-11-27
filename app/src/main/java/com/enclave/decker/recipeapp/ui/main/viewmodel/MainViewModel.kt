package com.enclave.decker.recipeapp.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.enclave.decker.recipeapp.base.BaseViewModel
import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.roomdatabase.usecases.AddRecipe
import com.enclave.decker.recipeapp.roomdatabase.usecases.GetRecipe
import com.enclave.decker.recipeapp.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject
import io.reactivex.Observable
import com.enclave.decker.recipeapp.roomdatabase.base.Result
import com.enclave.decker.recipeapp.roomdatabase.usecases.DeleteRecipe
import com.enclave.decker.recipeapp.roomdatabase.usecases.GetFilterRecipe
import io.reactivex.subjects.PublishSubject

class MainViewModel @Inject constructor(
    private val context: Context,
    private var getRecipe: GetRecipe,
    private var filterRecipe: GetFilterRecipe,
    private var addRecipe: AddRecipe,
    private var deleteRecipe: DeleteRecipe
) : BaseViewModel() {

    private val addRecipeSuccessSubject = PublishSubject.create<Unit>()
    val addRecipeSuccess: Observable<Unit> = addRecipeSuccessSubject.hide()

    val recipes = MutableLiveData<List<Recipe>>()
    val isExecuting = MutableLiveData<Boolean>()

    init {
        getAllRecipe()
    }

    fun getAllRecipe(){
        compositeDisposable += getRecipe.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recipes.value = it
            }
    }

    fun filterRecipe(type: String){
        compositeDisposable += filterRecipe.execute(type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recipes.value = it
            }
    }

    fun addRecipe() {
        compositeDisposable += addRecipe.execute(
            AddRecipe.RecipeAdd(
                "Chocolate Cake",
                "Fast Food",
                "- Chocolate\n- Flour\n- Milk\n- Sugar\n- Eggs",
                "1. Mix Eggs with Milk\n2. Mix Flour with Chocolate and Sugar\n3. Mix all together\n4. Bake for 40min"
            )
        )
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

    fun editRecipe(recipe: Recipe){
        compositeDisposable += deleteRecipe.execute(recipe.id!!)
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

    fun deleteRecipe(recipe: Recipe){
        compositeDisposable += deleteRecipe.execute(recipe.id!!)
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
}