package com.enclave.decker.recipeapp.roomdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.enclave.decker.recipeapp.roomdatabase.entity.RecipeDto

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe_table")
    fun getAllRecipes(): List<RecipeDto>

    @Query("SELECT * FROM recipe_table WHERE id = :id")
    fun getRecipeWithId(id: Int): List<RecipeDto>

    @Query("SELECT * FROM recipe_table WHERE type = :type")
    fun getRecipe(type: String): RecipeDto?

    @Query("SELECT * FROM recipe_table WHERE type = :type")
    fun getRecipeWithType(type: String): List<RecipeDto>

    @Insert
    fun insertRecipe(recipe: RecipeDto)

    @Query("DELETE FROM recipe_table WHERE id = :recipeId")
    fun deleteVenues(recipeId: Int)
}