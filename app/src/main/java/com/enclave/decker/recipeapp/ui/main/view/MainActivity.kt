package com.enclave.decker.recipeapp.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.enclave.decker.recipeapp.R
import com.enclave.decker.recipeapp.base.BaseBindingActivity
import com.enclave.decker.recipeapp.base.adapters.RecipeSpinnerAdapter
import com.enclave.decker.recipeapp.databinding.ActivityMainBinding
import com.enclave.decker.recipeapp.ui.main.viewmodel.MainViewModel
import com.enclave.decker.recipeapp.utils.XMLPullParserHandler

class MainActivity : BaseBindingActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId = R.layout.activity_main
    override val viewModelClass = MainViewModel::class

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        setupVersionSpinner()
        binding.fab.setOnClickListener {
            viewModel.saveScore()
        }
    }

    private fun setupVersionSpinner() {
        val parser = XMLPullParserHandler()
        val recipeTypes = parser.parse(assets.open("recipetypes.xml"))
        val adapter = RecipeSpinnerAdapter(
            this,
            recipeTypes
        ) { it.name }

        binding.spRecipe.adapter = adapter

        binding.spRecipe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            }
        }
    }
}