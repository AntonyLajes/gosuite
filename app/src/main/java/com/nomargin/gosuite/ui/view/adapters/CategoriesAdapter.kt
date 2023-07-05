package com.nomargin.gosuite.ui.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.CategoriesItemBinding
import com.nomargin.gosuite.util.models.CategoryModel

class CategoriesAdapter(
    private val context: Context,
    private val categoriesList: List<CategoryModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentCategory = categoriesList[position]
        holder.itemBinding.categoryTitle.text = currentCategory.categoryName
        holder.itemBinding.categoryField.setOnClickListener {
            selectedPosition = position
            onItemClickListener.onItemClickListener(currentCategory, position)
            notifyDataSetChanged()
        }
        if (selectedPosition == position) {
            holder.itemBinding.categoryField.background = context.getDrawable(R.drawable.rounded_green_background)
            holder.itemBinding.categoryTitle.setTextColor(context.getColor(R.color.white))
        } else {
            holder.itemBinding.categoryField.background = context.getDrawable(R.drawable.rounded_white_background)
            holder.itemBinding.categoryTitle.setTextColor(context.getColor(R.color.color_primary))
        }
    }

    override fun getItemCount(): Int = categoriesList.size

    class MyViewHolder(val itemBinding: CategoriesItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener {
        fun onItemClickListener(filteredCategory: CategoryModel,position: Int)
    }
}