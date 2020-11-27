package com.enclave.decker.recipeapp.ui.main.di

import androidx.lifecycle.ViewModel
import com.enclave.decker.recipeapp.di.module.ViewModelKey
import com.enclave.decker.recipeapp.ui.main.navigator.AndroidMainNavigator
import com.enclave.decker.recipeapp.ui.main.navigator.MainNavigator
import com.enclave.decker.recipeapp.ui.main.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    abstract fun main(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun navigator(navigator: AndroidMainNavigator): MainNavigator
}

