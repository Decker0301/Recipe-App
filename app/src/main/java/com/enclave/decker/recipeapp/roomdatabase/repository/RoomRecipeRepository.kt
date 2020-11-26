package com.enclave.decker.recipeapp.roomdatabase.repository

import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.roomdatabase.RoomRecipeDatabase
import com.enclave.decker.recipeapp.roomdatabase.entity.RecipeDto
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RoomRecipeRepository @Inject constructor(
    scoreDatabase: RoomRecipeDatabase
) : RecipeDatabase {

    private val highScoreDao = scoreDatabase.recipeDao()

    override val readAll: Observable<List<Recipe>> = Observable.fromCallable {
        highScoreDao.getAllRecipes().map { recipeDto ->
            Recipe(
                title = recipeDto.title,
                type = recipeDto.type,
                ingredients = recipeDto.ingredients,
                steps = recipeDto.steps
            )
        }
    }

//    override fun readAllWithType(type: String): Maybe<List<Recipe>> = Maybe.fromCallable {
//        highScoreDao.getRecipeWithType(type).map { recipeDto ->
//            Recipe(
//                title = recipeDto.title,
//                type = recipeDto.type,
//                ingredients = recipeDto.ingredients,
//                steps = recipeDto.steps
//            )
//        }
//    }

    override fun addRecipe(title: String, type: String, ingredient: String, step: String): Completable = Completable.fromCallable {
        highScoreDao.insertRecipe(
            RecipeDto(
                title = title,
                type = type,
                ingredients = ingredient,
                steps = step
            )
        )
        highScoreDao.getRecipeWithType(type)!!.toRecipe()
    }

//    override fun addRecipe(new: Recipe): Completable = Completable.fromAction {
//        highScoreDao.insertRecipe(RecipeDto.fromRecipeDetails(new))
//    }
}