package com.nomargin.gosuite.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.databinding.HorizontalProductItemBinding
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.OnItemClickListener
import com.nomargin.gosuite.util.models.FurnitureModel

class SearchAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var productsList: List<FurnitureModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = HorizontalProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productsList[position]
        holder.itemBinding.furnitureName.text = context.getString(currentItem.furnitureName)
        holder.itemBinding.furnitureCategory.text = currentItem.furnitureCategory[1]
        holder.itemBinding.furniturePrice.text = "${'$'}${currentItem.furniturePrice}"
        holder.itemBinding.furnitureImage.setImageResource(currentItem.furnitureImage)
        holder.itemBinding.furnitureRate.text = currentItem.furnitureRate.toString()
        holder.itemBinding.bestSellerField.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.goToDetailsMode, currentItem, position)
        }
        holder.itemBinding.buttonAddToCart.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.goToCartMode, currentItem, position)
        }
    }

    override fun getItemCount(): Int = productsList.size

    fun getProductsList(list: List<FurnitureModel>){
        productsList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val itemBinding: HorizontalProductItemBinding): RecyclerView.ViewHolder(itemBinding.root)

}