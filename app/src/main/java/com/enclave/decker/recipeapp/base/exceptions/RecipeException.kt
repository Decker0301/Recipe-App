package com.enclave.decker.recipeapp.base.exceptions

import com.enclave.decker.recipeapp.base.validation.ValidatableField
import com.enclave.decker.recipeapp.base.validation.ValidationProblem


sealed class RecipeException : Throwable() {
    class ValidationException(val problems: List<Pair<ValidatableField, ValidationProblem>>) : RecipeException()
}
