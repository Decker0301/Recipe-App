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
        viewModel.addRecipeSuccess bindTo this::onReviewDeleteSuccess
    }

    override fun initView(savedInstanceState: Bundle?) {
        setupVersionSpinner()

        registerForContextMenu(binding.rvRecipe)

        binding.rvRecipe.adapter = RecipeAdapter(
            this,
            this@MainActivity,
            viewModel.recipes,
//            viewModel::editRecipe,
            viewModel::deleteRecipe
        ){}

        binding.fab.setOnClickListener {
            viewModel.addRecipe()
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
                val type = adapter.getItem(position)?.name.toString()
                viewModel.filterRecipe(type)
            }
        }
    }

    private fun onReviewDeleteSuccess() {
        viewModel.getAllRecipe()
    }
}