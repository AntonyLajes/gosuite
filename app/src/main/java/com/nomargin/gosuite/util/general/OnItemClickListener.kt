package com.nomargin.gosuite.util.general

interface OnItemClickListener {

    fun <T> onItemClickListener(mode: Int, item: T, position: Int)

    fun <T> onItemCheckedListener(isChecked: Boolean, item: T, position: Int)
}