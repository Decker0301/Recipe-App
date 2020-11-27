package com.enclave.decker.recipeapp.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enclave.decker.recipeapp.model.Recipe

@Entity(tableName = "recipe_table")
data class RecipeDto(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val type: String,
    val ingredients: String?,
    val steps: String?

) {
    fun toRecipe() = Recipe(
        id = id,
        title = title,
        type = type,
        ingredients = ingredients,
        steps = steps
    )
}