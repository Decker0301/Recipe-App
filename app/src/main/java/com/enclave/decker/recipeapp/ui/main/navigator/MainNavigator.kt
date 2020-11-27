package com.enclave.decker.recipeapp.ui.main.navigator

import com.enclave.decker.recipeapp.model.Recipe

interface MainNavigator {
    fun navigate(event: Event)

    sealed class Event {
        object NavigateRecipeDetails: Event()
        data class NavigateEditRecipe(val recipeID: Int?): Event()
    }
}