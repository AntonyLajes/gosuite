package com.nomargin.gosuite.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.databinding.FavoriteItemBinding
import com.nomargin.gosuite.util.models.FurnitureModel

class FavoriteAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    private var favoriteList: ArrayList<FurnitureModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = favoriteList[position]
        holder.itemBinding.furnitureName.text = context.getString(currentItem.furnitureName)
        holder.itemBinding.furnitureCategory.text = currentItem.furnitureCategory[1]
        holder.itemBinding.furniturePrice.text = "${'$'}${currentItem.furniturePrice}"
        holder.itemBinding.furnitureRate.text = currentItem.furnitureRate.toString()
        holder.itemBinding.furnitureImage.setImageResource(currentItem.furnitureImage)
        holder.itemBinding.furnitureCheckBox.isChecked = false
        holder.itemBinding.furnitureCheckBox.setOnClickListener {
            onItemClickListener.onCheckedListener(currentItem, holder.itemBinding.furnitureCheckBox.isChecked, position)
        }
        holder.itemBinding.buttonAddToCart.setOnClickListener {
            onItemClickListener.onItemClickListener(currentItem, position)
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    fun getFavoriteList(list: ArrayList<FurnitureModel>) {
        favoriteList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val itemBinding: FavoriteItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener{
        fun onItemClickListener(furnitureData: FurnitureModel, position: Int)
        fun onCheckedListener(furnitureData: FurnitureModel, isChecked: Boolean, position: Int)
    }
}