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
import com.enclave.decker.recipeapp.ui.main.navigator.MainNavigator.Event
import io.reactivex.subjects.PublishSubject

class MainViewModel @Inject constructor(
    private val context: Context,
    private var getRecipe: GetRecipe,
    private var filterRecipe: GetFilterRecipe,
    private var addRecipe: AddRecipe,
    private var deleteRecipe: DeleteRecipe
) : BaseViewModel() {

    private val navigationSubject = PublishSubject.create<Event>()
    val navigation: Observable<Event> = navigationSubject.hide()

    private val deleteRecipeSuccessSubject = PublishSubject.create<Unit>()
    val deleteRecipeSuccess: Observable<Unit> = deleteRecipeSuccessSubject.hide()

    val recipes = MutableLiveData<List<Recipe>>()
    val isExecuting = MutableLiveData<Boolean>()

    val filter = MutableLiveData<String>()


    init {
        getAllRecipe()
    }

    fun getAllRecipe(){
        compositeDisposable += getRecipe.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isExecuting.value = true }
            .doFinally { isExecuting.value = false }
            .subscribe {
                recipes.value = it
            }
    }

    fun filterRecipe(){
        compositeDisposable += filterRecipe.execute(filter.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recipes.value = it
            }
    }

    fun deleteRecipe(recipe: Recipe){
        compositeDisposable += deleteRecipe.execute(recipe.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isExecuting.value = true }
            .doFinally { isExecuting.value = false }
            .subscribe(Consumer {
                when (it) {
                    is Result.Success -> deleteRecipeSuccessSubject.onNext(Unit)
                    is Result.Error -> {
                    }
                }
            })
    }

    fun showRecipeDetails() {
        navigationSubject.onNext(
            Event.NavigateRecipeDetails
        )
    }

    fun editRecipe(recipe: Recipe){
        navigationSubject.onNext(
            Event.NavigateEditRecipe(recipe.id)
        )
    }
}