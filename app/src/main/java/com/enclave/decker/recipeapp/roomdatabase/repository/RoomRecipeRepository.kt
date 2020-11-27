package com.enclave.decker.recipeapp.roomdatabase.repository

import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.roomdatabase.RoomRecipeDatabase
import com.enclave.decker.recipeapp.roomdatabase.entity.RecipeDto
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomRecipeRepository @Inject constructor(
    recipeDatabase: RoomRecipeDatabase
) : RecipeDatabase {

    private val recipeDao = recipeDatabase.recipeDao()

    override val readAll: Observable<List<Recipe>> = Observable.fromCallable {
        recipeDao.getAllRecipes().map { recipeDto ->
            Recipe(
                id = recipeDto.id,
                title = recipeDto.title,
                type = recipeDto.type,
                ingredients = recipeDto.ingredients,
                steps = recipeDto.steps
            )
        }
    }

    override fun readAllWithType(type: String): Observable<List<Recipe>> = Observable.fromCallable {
        recipeDao.getRecipeWithType(type).map { recipeDto ->
            Recipe(
                id = recipeDto.id,
                title = recipeDto.title,
                type = recipeDto.type,
                ingredients = recipeDto.ingredients,
                steps = recipeDto.steps
            )
        }
    }

    override fun readAllWithId(id: Int): Observable<Recipe> = Observable.fromCallable {
        recipeDao.getRecipeWithId(id)?.toRecipe()
    }

    override fun addRecipe(title: String, type: String, ingredient: String?, step: String?): Completable = Completable.fromCallable {
        recipeDao.insertRecipe(
            RecipeDto(
                title = title,
                type = type,
                ingredients = ingredient,
                steps = step
            )
        )
        recipeDao.getRecipe(type)!!.toRecipe()
    }

    override fun updateRecipe(id: Int, title: String, type: String, ingredient: String?, step: String?): Completable = Completable.fromCallable {
        recipeDao.insertRecipe(
            RecipeDto(
                title = title,
                type = type,
                ingredients = ingredient,
                steps = step
            )
        )
    }

    override fun deleteRecipe(id: Int): Completable = Completable.fromCallable {
        recipeDao.deleteVenues(id)
    }
}