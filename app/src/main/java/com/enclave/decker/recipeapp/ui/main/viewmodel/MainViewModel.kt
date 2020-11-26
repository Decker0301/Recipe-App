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
import com.enclave.decker.recipeapp.roomdatabase.base.Result
import io.reactivex.subjects.PublishSubject

class MainViewModel @Inject constructor(
    private val context: Context,
    getRecipe: GetRecipe,
    private var addRecipe: AddRecipe
) : BaseViewModel() {

    private val saveScoreSuccessSubject = PublishSubject.create<Unit>()
    val saveScoreSuccess = saveScoreSuccessSubject.hide()

    val recipes = MutableLiveData<List<Recipe>>()
    val isExecuting = MutableLiveData<Boolean>()

    init {
        compositeDisposable += getRecipe.execute(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recipes.value = it
            }
    }

    fun saveScore() {
        compositeDisposable += addRecipe.execute(
            AddRecipe.RecipeAdd(
                "Test", "Type", "1 2 3", "1"
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isExecuting.value = true }
            .doFinally { isExecuting.value = false }
            .subscribe(Consumer {
                when (it) {
                    is Result.Success -> saveScoreSuccessSubject.onNext(Unit)
                    is Result.Error -> {
                    }
                }
            })

    }
}