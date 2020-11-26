package com.enclave.decker.recipeapp

import com.enclave.decker.recipeapp.di.component.DaggerAppComponent
import com.enclave.decker.recipeapp.di.component.DaggerNotificationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

open class RecipeApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler{}
    }

    override fun applicationInjector(): AndroidInjector<RecipeApplication> = DaggerAppComponent.builder()
        .notification(
            DaggerNotificationComponent.builder().apply {
                context(applicationContext)
            }.build())
        .create(this)
}