package com.nomargin.gosuite.util.general

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nomargin.gosuite.R
import com.nomargin.gosuite.ui.view.adapters.CartAdapter
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.models.CartModel
import com.nomargin.gosuite.util.models.FurnitureModel

class GeneralFunctions(private val activity: Activity) {

    private val sharedPreferences = activity.applicationContext.getSharedPreferences(
        ApplicationConstants.SharedPreferencesKeys.sharedPreferencesGeneralKey,
        Context.MODE_PRIVATE
    )
    private val sharedPreferencesEditor = sharedPreferences.edit()
    private val gson = Gson()
    private val stringBuilder = StringBuilder()

    fun getFurnitureSharedPreferencesArray(key: String): java.util.ArrayList<FurnitureModel>? {
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<java.util.ArrayList<FurnitureModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun getCartSharedPreferencesArray(key: String): java.util.ArrayList<CartModel>? {
        val json = sharedPreferences.getString(key, null)
        val type = object : TypeToken<java.util.ArrayList<CartModel>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearSharedPreferencesArray(key: String) {
        sharedPreferencesEditor.remove(key).apply()
    }

    fun <T> saveFurnitureSharedPreferencesArray(furnitureList: ArrayList<T>, key: String) {
        val json = gson.toJson(furnitureList)
        sharedPreferencesEditor.putString(key, json)
        sharedPreferencesEditor.apply()
    }

    fun goToCart(mode: Int, cartItem: CartModel) {
        val cartList: ArrayList<CartModel> =
            getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                ?: ArrayList()
        val furnitureName = activity.getString(cartItem.furniture.furnitureName)
        val hasFurnitureAddedToCart = cartList.any {
            it.furniture == cartItem.furniture
        }
        if (!hasFurnitureAddedToCart) {
            cartList.add(CartModel(cartItem.furniture, 1, false))
            saveFurnitureSharedPreferencesArray(
                cartList,
                ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey
            )
            handlerCartMessage(furnitureName, R.string.added_cart)
        } else {
            when (mode) {
                ApplicationConstants.Mode.cartButtonAddQuantityMode -> {
                    for(item in cartList){
                        if(item.furniture == cartItem.furniture){
                            item.furnitureQuantity += 1
                        }
                    }
                    updateCartList(cartList, cartItem)
                }
                ApplicationConstants.Mode.cartButtonRemoveQuantityMode -> {
                    if (cartList.find { it.furniture == cartItem.furniture }?.furnitureQuantity!! > 1) {
                        cartList.find { it.furniture == cartItem.furniture }?.furnitureQuantity =
                            cartList.find { it.furniture == cartItem.furniture }?.furnitureQuantity?.minus(
                                1
                            )!!
                        updateCartList(cartList, cartItem)
                    }
                }
                else -> {
                    handlerCartMessage(furnitureName, R.string.already_added_cart)
                }
            }
        }
    }

    private fun updateCartList(
        cartList: ArrayList<CartModel>,
        cartItem: CartModel
    ) {
        cartList.find { it.furniture == cartItem.furniture }?.isChecked = cartItem.isChecked
        saveFurnitureSharedPreferencesArray(
            cartList,
            ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey
        )
    }

    fun handlerFavoriteMessage(furnitureName: String, stringPath: Int) {
        stringBuilder.clear()
        stringBuilder
            .append(activity.getString(R.string.item))
            .append(activity.getString(R.string.space))
            .append(activity.getString(R.string.open_quote))
            .append(furnitureName)
            .append(activity.getString(R.string.open_quote))
            .append(activity.getString(R.string.space))
            .append(activity.getString(stringPath))

        makeToast(stringBuilder.toString())
    }

    fun handlerCartMessage(furnitureName: String, stringPath: Int) {
        stringBuilder.clear()
        stringBuilder
            .append(activity.getString(R.string.open_quote))
            .append(furnitureName)
            .append(activity.getString(R.string.open_quote))
            .append(activity.getString(R.string.space))
            .append(activity.getString(stringPath))

        makeToast(stringBuilder.toString())
    }

    private fun makeToast(message: String) {
        Toast.makeText(activity.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}