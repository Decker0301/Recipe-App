package com.enclave.decker.recipeapp.base.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class RecipeSpinnerAdapter<E>(
    context: Context,
    options: List<E>,
    private val textSelector: (E) -> CharSequence?
) : ArrayAdapter<E>(context, android.R.layout.simple_spinner_item, options) {

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val text = view.findViewById<TextView>(android.R.id.text1)
        text.text = getItem(position)?.let { textSelector(it) }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val text = view.findViewById<TextView>(android.R.id.text1)
        text.text = getItem(position)?.let { textSelector(it) }

        return view
    }
}
