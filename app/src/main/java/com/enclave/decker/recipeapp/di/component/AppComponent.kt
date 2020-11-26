package com.enclave.decker.recipeapp.di.component

import com.enclave.decker.recipeapp.RecipeApplication
import com.enclave.decker.recipeapp.di.module.AppInjectorsModule
import com.enclave.decker.recipeapp.di.module.ViewModelsModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NotificationComponent::class],
    modules = [
        ViewModelsModule::class,
        AndroidSupportInjectionModule::class,
        AppInjectorsModule::class
    ]
)

interface AppComponent : AndroidInjector<RecipeApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RecipeApplication>() {
            abstract fun notification(notification: NotificationComponent): Builder
    }
}