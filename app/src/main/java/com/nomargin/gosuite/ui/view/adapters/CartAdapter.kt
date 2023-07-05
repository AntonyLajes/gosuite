package com.nomargin.gosuite.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomargin.gosuite.databinding.CartItemBinding
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.models.CartModel

class CartAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<CartAdapter.MyViewHolder>(){

    private var cartList: ArrayList<CartModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = cartList[position]
        holder.itemBinding.furnitureName.text = context.getString(currentItem.furniture.furnitureName)
        holder.itemBinding.furnitureCategory.text = currentItem.furniture.furnitureCategory[1]
        holder.itemBinding.furniturePrice.text = currentItem.furniture.furniturePrice.toString()
        holder.itemBinding.furnitureImage.setImageResource(currentItem.furniture.furnitureImage)
        holder.itemBinding.quantity.text = currentItem.furnitureQuantity.toString()
        holder.itemBinding.furnitureCheckBox.isChecked = currentItem.isChecked
        holder.itemBinding.furnitureCheckBox.setOnClickListener {
            currentItem.isChecked = !currentItem.isChecked
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.cartCheckItemMode, holder.itemBinding.furnitureCheckBox.isChecked, currentItem, position)
        }
        holder.itemBinding.buttonAddQuantity.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.cartButtonAddQuantityMode, holder.itemBinding.furnitureCheckBox.isChecked, currentItem, position)
        }
        holder.itemBinding.buttonRemoveQuantity.setOnClickListener {
            onItemClickListener.onItemClickListener(ApplicationConstants.Mode.cartButtonRemoveQuantityMode, holder.itemBinding.furnitureCheckBox.isChecked, currentItem, position)
        }
    }

    override fun getItemCount(): Int = cartList.size

    fun getCartList(list: ArrayList<CartModel>){
        cartList = list
        notifyDataSetChanged()
    }

    fun updateCartList(item: CartModel, position: Int){
        cartList[position] = item
        notifyItemChanged(position)
    }

    class MyViewHolder(var itemBinding: CartItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    interface OnItemClickListener{
        fun onItemClickListener(mode: Int, isChecked: Boolean, cartItem: CartModel, position: Int)
    }
}