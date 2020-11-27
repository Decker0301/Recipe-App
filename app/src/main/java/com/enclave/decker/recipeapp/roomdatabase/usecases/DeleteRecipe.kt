package com.enclave.decker.recipeapp.roomdatabase.usecases

import com.enclave.decker.recipeapp.roomdatabase.base.Result
import com.enclave.decker.recipeapp.roomdatabase.base.UseCase
import com.enclave.decker.recipeapp.roomdatabase.base.asSuccess
import com.enclave.decker.recipeapp.roomdatabase.repository.RecipeDatabase
import io.reactivex.Single
import javax.inject.Inject

class DeleteRecipe @Inject constructor(
    private val database: RecipeDatabase
) : UseCase.Single<Int, Unit> {

    override fun execute(recipeId: Int): Single<Result<Unit>> =
        database.deleteRecipe(recipeId).andThen(Single.just(Unit.asSuccess()))
}