package com.enclave.decker.recipeapp.ui.recipedetails.di

import androidx.lifecycle.ViewModel
import com.enclave.decker.recipeapp.di.module.ViewModelKey
import com.enclave.decker.recipeapp.ui.recipedetails.viewmodel.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailsModule {

    @IntoMap
    @Binds
    @ViewModelKey(DetailsViewModel::class)
    abstract fun main(viewModel: DetailsViewModel): ViewModel
}

