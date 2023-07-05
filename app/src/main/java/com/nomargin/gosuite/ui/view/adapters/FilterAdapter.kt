package com.nomargin.gosuite.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.databinding.FilterItemBinding
import com.nomargin.gosuite.util.general.OnItemClickListener
import com.nomargin.gosuite.util.models.CategoryModel

class FilterAdapter(private val filterList: List<CategoryModel>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<FilterAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filterList[position]
        holder.itemBinding.checkBox.isChecked = false
        holder.itemBinding.checkBox.text = currentItem.categoryName
        holder.itemBinding.checkBox.setOnClickListener {
            onItemClickListener.onItemCheckedListener(holder.itemBinding.checkBox.isChecked, currentItem, position)
        }
        if(position == 0){
            holder.itemBinding.checkBox.isChecked = true
        }
    }

    override fun getItemCount(): Int = filterList.size

    class MyViewHolder(val itemBinding: FilterItemBinding):RecyclerView.ViewHolder(itemBinding.root)

}