package com.enclave.decker.recipeapp.roomdatabase.usecases

import com.enclave.decker.recipeapp.roomdatabase.base.UseCase
import com.enclave.decker.recipeapp.roomdatabase.base.Result
import com.enclave.decker.recipeapp.roomdatabase.base.asSuccess
import com.enclave.decker.recipeapp.roomdatabase.repository.RecipeDatabase
import io.reactivex.Single
import javax.inject.Inject

class AddRecipe @Inject constructor(
    private val database: RecipeDatabase
) : UseCase.Single<AddRecipe.RecipeAdd, Unit> {
    data class RecipeAdd(val title: String, val type: String, val ingredient: String, val step: String)

    override fun execute(param: RecipeAdd): Single<Result<Unit>> =
        database.addRecipe(param.title, param.type, param.ingredient, param.step)
            .andThen(Single.just(Unit.asSuccess()))
    }