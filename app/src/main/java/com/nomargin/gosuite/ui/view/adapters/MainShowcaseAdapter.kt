package com.nomargin.gosuite.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.databinding.MainShowcaseItemBinding
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.OnItemClickListener
import com.nomargin.gosuite.util.models.FurnitureModel

class MainShowcaseAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<MainShowcaseAdapter.MyViewHolder>() {

    private var furnitureList: List<FurnitureModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MainShowcaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = furnitureList[position]
        holder.itemBinding.fornitureName.text = context.getString(currentItem.furnitureName)
        holder.itemBinding.fornitureCategory.text = currentItem.furnitureCategory[1]
        holder.itemBinding.furniturePrice.text = "${'$'}${currentItem.furniturePrice}"
        holder.itemBinding.furnitureRate.text = currentItem.furnitureRate.toString()
        holder.itemBinding.fornitureImage.setImageResource(currentItem.furnitureImage)
        holder.itemBinding.mainShowcaseField.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.goToDetailsMode, currentItem, position)
        }
        holder.itemBinding.buttonAddToCart.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.goToCartMode, currentItem, position)
        }
    }

    override fun getItemCount(): Int = furnitureList.size

    fun getFurnitureList(list: List<FurnitureModel>){
        furnitureList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val itemBinding: MainShowcaseItemBinding): RecyclerView.ViewHolder(itemBinding.root)
}