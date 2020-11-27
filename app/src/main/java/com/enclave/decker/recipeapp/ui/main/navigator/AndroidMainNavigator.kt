package com.enclave.decker.recipeapp.ui.main.navigator

import com.enclave.decker.recipeapp.model.Recipe
import com.enclave.decker.recipeapp.ui.main.view.MainActivity
import com.enclave.decker.recipeapp.ui.recipedetails.view.DetailsActivity
import javax.inject.Inject

class AndroidMainNavigator @Inject constructor(
    private val activity: MainActivity
) : MainNavigator {

    override fun navigate(event: MainNavigator.Event) =
        when (event) {
            is MainNavigator.Event.NavigateRecipeDetails -> showRecipeDetails()
            is MainNavigator.Event.NavigateEditRecipe -> showEditRecipe(event.recipeID)
        }

    private fun showRecipeDetails() {
        activity.run {
            startActivity(DetailsActivity.prepareIntent(this))
        }
    }

    private fun showEditRecipe(recipeId: Int?){
        activity.run {
            startActivity(DetailsActivity.prepareIntent(this, recipeId))
        }
    }
}