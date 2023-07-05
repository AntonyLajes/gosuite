package com.nomargin.gosuite.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.FragmentCartBinding
import com.nomargin.gosuite.ui.view.activities.AboutMeActivity
import com.nomargin.gosuite.ui.view.adapters.CartAdapter
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.GeneralFunctions
import com.nomargin.gosuite.util.models.CartModel
import java.lang.StringBuilder

class CartFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCartBinding? = null
    private var totalCheckedPrice = 0F
    private var totalItems = 0
    private val binding: FragmentCartBinding get() = _binding!!
    private val checkedCartList: ArrayList<CartModel> = arrayListOf()
    private val stringBuilder = StringBuilder()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var generalFunctions: GeneralFunctions
    private lateinit var cartList: ArrayList<CartModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater)
        generalFunctions = GeneralFunctions(requireActivity())
        initCartAdapter()
        handlerFields()
        uncheckItems()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initClicks()
        uncheckItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonDelete.id -> {
                for(checkedItem in checkedCartList){
                    cartList.filter {it.furniture == checkedItem.furniture }.forEach { item ->
                        cartList.remove(item)
                    }
                }
                generalFunctions.saveFurnitureSharedPreferencesArray(
                    cartList,
                    ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey
                )
                cartAdapter.getCartList(cartList)
                checkedCartList.clear()
                handlerFields()
            }
            binding.buttonProceedToCheckout.id -> {
                startActivity(Intent(requireContext(), AboutMeActivity::class.java))
            }
        }
    }

    private fun uncheckItems() {
        cartList.filter { it.isChecked }.forEach{
            !it.isChecked
        }
    }

    private fun initClicks() {
        binding.buttonDelete.setOnClickListener(this)
        binding.buttonProceedToCheckout.setOnClickListener(this)
    }

    private fun initCartAdapter() {
        cartList =
            generalFunctions.getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                ?: ArrayList()
        cartAdapter = CartAdapter(requireContext(), object : CartAdapter.OnItemClickListener {

            override fun onItemClickListener(
                mode: Int,
                isChecked: Boolean,
                cartItem: CartModel,
                position: Int
            ) {
                when (mode) {
                    ApplicationConstants.Mode.cartButtonAddQuantityMode -> {
                        generalFunctions.goToCart(
                            ApplicationConstants.Mode.cartButtonAddQuantityMode,
                            cartItem
                        )
                        verifyPositionAndUpdate(cartItem, position)
                    }
                    ApplicationConstants.Mode.cartButtonRemoveQuantityMode -> {
                        generalFunctions.goToCart(
                            ApplicationConstants.Mode.cartButtonRemoveQuantityMode,
                            cartItem
                        )
                        verifyPositionAndUpdate(cartItem, position)
                    }
                }
                handlerCheckedList(cartItem)
            }
        })
        cartAdapter.getCartList(cartList)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter
    }

    private fun handlerCheckedList(cartItem: CartModel) {
        if (!checkedCartList.any { it.furniture == cartItem.furniture }) {
            if(cartItem.isChecked){
                checkedCartList.add(cartItem)
            }
        }else{
            if(!cartItem.isChecked){
                checkedCartList.remove(cartItem)
            }
        }
        handlerFields()
    }

    private fun verifyPositionAndUpdate(
        cartItem: CartModel,
        position: Int
    ) {
        for (item in generalFunctions.getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
            ?: ArrayList()) {
            if (item.furniture == cartItem.furniture) {
                cartAdapter.updateCartList(item, position)
                handlerCheckedList(item)
            }
        }
    }

    private fun handlerFields() {
        binding.cardViewButtonDelete.isVisible = checkedCartList.isNotEmpty()
        binding.bottomView.isVisible = checkedCartList.isNotEmpty()
        binding.totalItems.isVisible = checkedCartList.isNotEmpty()
        binding.totalValue.isVisible = checkedCartList.isNotEmpty()
        binding.buttonProceedToCheckout.isVisible = checkedCartList.isNotEmpty()
        binding.emptyCartWarning.isVisible = cartList.isEmpty()
        handlerTextProceedToCheckout()
    }

    private fun handlerTextProceedToCheckout() {
        totalCheckedPrice = 0F
        totalItems = 0

        for (item in cartList) {
            if (item.isChecked) {
                totalCheckedPrice += item.furniture.furniturePrice * item.furnitureQuantity
                totalItems += item.furnitureQuantity
            }
        }
        val totalCheckedPriceFormatted = String.format("%.2f", totalCheckedPrice)

        stringBuilder.clear()
        stringBuilder
            .append(getString(R.string.currency_sign))
            .append(getString(R.string.space))
            .append(totalCheckedPriceFormatted)
        binding.totalValue.text = stringBuilder.toString()

        stringBuilder.clear()
        stringBuilder
            .append(getString(R.string.total))
            .append(getString(R.string.space))
            .append(getString(R.string.open_parentheses))
            .append(totalItems)
            .append(getString(R.string.space))
            .append(getString(R.string.items))
            .append(getString(R.string.close_parentheses))
            .append(getString(R.string.double_dots))

        binding.totalItems.text = stringBuilder.toString()
    }
}