package com.nomargin.gosuite.util.models

import android.os.Parcelable
import com.nomargin.gosuite.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class FurnitureModel(
    val furnitureName: Int,
    val furnitureDescription: Int,
    val furniturePrice: Float,
    val furnitureCategory: ArrayList<String>,
    val furnitureRate: Float,
    val isBestSeller: Boolean = false,
    val furnitureImage: Int = R.drawable.standard_furniture
) : Parcelable
