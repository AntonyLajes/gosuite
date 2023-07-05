package com.nomargin.gosuite.util.models

data class CartModel(
    var furniture: FurnitureModel,
    var furnitureQuantity: Int,
    var isChecked: Boolean
)
