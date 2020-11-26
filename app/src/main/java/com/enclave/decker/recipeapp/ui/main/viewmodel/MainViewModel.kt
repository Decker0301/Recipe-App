package com.enclave.decker.recipeapp.ui.main.viewmodel

import android.content.Context
import com.enclave.decker.recipeapp.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val context: Context
) : BaseViewModel()