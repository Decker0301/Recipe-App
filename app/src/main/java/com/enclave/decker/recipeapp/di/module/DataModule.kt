package com.enclave.decker.recipeapp.di.module

import android.content.Context
import com.enclave.decker.recipeapp.roomdatabase.RoomRecipeDatabase
import com.enclave.decker.recipeapp.roomdatabase.repository.RecipeDatabase
import com.enclave.decker.recipeapp.roomdatabase.repository.RoomRecipeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRecipeDatabase(context: Context): RoomRecipeDatabase = RoomRecipeDatabase.createDatabase(context)
    }

    @Binds
    @Singleton
    abstract fun recipeRepository(storage: RoomRecipeRepository) : RecipeDatabase
}