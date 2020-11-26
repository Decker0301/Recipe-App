package com.enclave.decker.recipeapp.roomdatabase.repository

import com.enclave.decker.recipeapp.model.Recipe
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

interface RecipeDatabase {

    val readAll: Observable<List<Recipe>>

//    fun readAllWithType(type: String): Maybe<List<Recipe>>

    fun addRecipe(title: String, type: String, ingredient: String, step: String): Completable
}