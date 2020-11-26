package com.enclave.decker.recipeapp.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.enclave.decker.recipeapp.model.Recipe

@Entity(tableName = "recipe_table")
data class RecipeDto(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    val type: String,
    val ingredients: String,
    val steps: String

) {
    companion object {

        @JvmStatic
        fun fromRecipeDetails(recipe: Recipe) = RecipeDto(
            title = recipe.title.toString(),
            type = recipe.type.toString(),
            ingredients = recipe.ingredients.toString(),
            steps = recipe.steps.toString()
        )
    }

    fun toRecipe() = Recipe(
        title = title,
        type = type,
        ingredients = ingredients,
        steps = steps
    )
}