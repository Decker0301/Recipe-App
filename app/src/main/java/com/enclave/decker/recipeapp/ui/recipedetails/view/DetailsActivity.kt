package com.enclave.decker.recipeapp.ui.recipedetails.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.enclave.decker.recipeapp.R
import com.enclave.decker.recipeapp.base.BaseBindingActivity
import com.enclave.decker.recipeapp.base.adapters.RecipeSpinnerAdapter
import com.enclave.decker.recipeapp.databinding.ActivityDetailsBinding
import androidx.lifecycle.Observer
import com.enclave.decker.recipeapp.ui.recipedetails.viewmodel.DetailsViewModel
import com.enclave.decker.recipeapp.utils.XMLPullParserHandler

class DetailsActivity : BaseBindingActivity<ActivityDetailsBinding, DetailsViewModel>() {

    companion object {
        private const val EXTRA_DATA = "recipeDetails"

        fun prepareIntent(context: Context) = Intent(context, DetailsActivity::class.java)

        fun prepareIntent(context: Context, recipeID: Int?): Intent =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(EXTRA_DATA, recipeID)
            }
    }

    override val layoutId = R.layout.activity_details
    override val viewModelClass = DetailsViewModel::class

    val recipeID: Int?
        get() = intent?.extras?.getInt(EXTRA_DATA)

    override fun init(savedInstanceState: Bundle?) {
        viewModel.addRecipeSuccess bindTo this::onRecipeAddSuccess
    }

    override fun initView(savedInstanceState: Bundle?) {
        setupVersionSpinner()
//            viewModel.recipeId.value = recipeID
//        viewModel.recipeId.observe(this, Observer {
//        })
        recipeID?.let {
            viewModel.getRecipe(recipeID)
        }
    }


    private fun setupVersionSpinner() {
        val parser = XMLPullParserHandler()
        val recipeTypes = parser.parse(assets.open("recipetypes.xml"))
        val adapter = RecipeSpinnerAdapter(
            this,
            recipeTypes
        ) { it.name }

        binding.spTypeRecipe.adapter = adapter

//        binding.spTypeRecipe.setSelection()

        binding.spTypeRecipe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
                viewModel.type.value = parent.selectedItem.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = adapter.getItem(position)?.name.toString()
                viewModel.type.value = type
            }
        }
    }

    private fun onRecipeAddSuccess() {
        finish()
    }
}