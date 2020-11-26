package com.enclave.decker.recipeapp.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enclave.decker.recipeapp.R
import com.enclave.decker.recipeapp.base.BaseBindingActivity
import com.enclave.decker.recipeapp.databinding.ActivityMainBinding
import com.enclave.decker.recipeapp.ui.main.viewmodel.MainViewModel
import kotlin.reflect.KClass

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}