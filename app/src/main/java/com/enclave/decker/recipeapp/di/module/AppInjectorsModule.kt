package com.enclave.decker.recipeapp.di.module

import android.content.Context
import com.enclave.decker.recipeapp.ui.main.view.MainActivity
import com.enclave.decker.recipeapp.RecipeApplication
import com.enclave.decker.recipeapp.di.ActivityScope
import com.enclave.decker.recipeapp.ui.main.di.MainModule
import com.enclave.decker.recipeapp.ui.recipedetails.di.DetailsModule
import com.enclave.decker.recipeapp.ui.recipedetails.view.DetailsActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppInjectorsModule {

    @Binds
    abstract fun context(app: RecipeApplication): Context

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun main(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun details(): DetailsActivity
}