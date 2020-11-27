package com.enclave.decker.recipeapp.roomdatabase.usecases


import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.roomdatabase.base.UseCase
import com.enclave.decker.recipeapp.roomdatabase.repository.RecipeDatabase
import io.reactivex.Observable
import javax.inject.Inject

class GetFilterRecipe @Inject constructor(
    private val database: RecipeDatabase
) : UseCase.Continuous<String, List<Recipe>> {

    override fun execute(param: String): Observable<List<Recipe>> = database.readAllWithType(param)
}