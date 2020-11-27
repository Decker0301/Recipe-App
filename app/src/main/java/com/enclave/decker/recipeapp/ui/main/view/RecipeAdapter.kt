package com.enclave.decker.recipeapp.ui.main.view

import android.content.Context
import android.graphics.Color
import android.view.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.enclave.decker.recipeapp.R
import com.enclave.decker.recipeapp.databinding.ViewItemRecipeBinding
import com.enclave.decker.recipeapp.model.Recipe


class RecipeAdapter(
    private val context: Context,
    lifecycleOwner: LifecycleOwner,
    private val data: LiveData<List<Recipe>>,
    private val onDeleteRecipeClicked: (Recipe) -> Unit,
    private val onEditRecipeClicked: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ReviewViewHolder>() {

    init {
        data.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
        setHasStableIds(true)
    }

    class ReviewViewHolder(val binding: ViewItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewItemRecipeBinding.inflate(inflater, parent, false)

        return ReviewViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val mColors = arrayOf("#F5E1C4", "#DECCBD")
        holder.binding.model = data.value?.getOrNull(position)

        holder.binding.viewRecipe.setBackgroundColor(Color.parseColor(mColors[position % 2]))

        holder.binding.root.setOnClickListener {
            val popupMenu: android.widget.PopupMenu = android.widget.PopupMenu(context, holder.binding.root)
            popupMenu.menuInflater.inflate(R.menu.menu_app,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(android.widget.PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.menu_edit ->{
                        holder.binding.model?.let(onEditRecipeClicked)
                        true
                    }
                    R.id.menu_delete ->{
                        holder.binding.model?.let(onDeleteRecipeClicked)
                        true
                    }
                    else -> false
                }
            })
            popupMenu.show()
        }

    }

    override fun getItemCount(): Int = data.value.orEmpty().size

}
