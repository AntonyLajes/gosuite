package com.nomargin.gosuite.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.FragmentFavoriteBinding
import com.nomargin.gosuite.ui.view.adapters.FavoriteAdapter
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.GeneralFunctions
import com.nomargin.gosuite.util.models.CartModel
import com.nomargin.gosuite.util.models.FurnitureModel


class FavoriteFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private var checkedFurniturePrice: Float = 0F
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val checkedFurnitureList: ArrayList<FurnitureModel> = arrayListOf()
    private lateinit var generalFunctions: GeneralFunctions
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteList: ArrayList<FurnitureModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        generalFunctions = GeneralFunctions(requireActivity())
        favoriteList =
            generalFunctions.getFurnitureSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey)
                ?: ArrayList()
        handlerFields()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initClicks()
        initFavoriteListAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonBack.id -> {
                findNavController().popBackStack()
            }
            binding.buttonDelete.id -> {
                for (furniture in checkedFurnitureList) {
                    favoriteList.remove(furniture)
                }
                generalFunctions.clearSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey)
                generalFunctions.saveFurnitureSharedPreferencesArray(
                    favoriteList,
                    ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey
                )
                checkedFurniturePrice = 0F
                checkedFurnitureList.clear()
                favoriteAdapter.getFavoriteList(favoriteList)
                handlerFields()
            }
            binding.buttonAddToCart.id -> {
                val cartList: ArrayList<CartModel> = generalFunctions.getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey) ?: ArrayList()
                for (furnitureData in checkedFurnitureList) {
                    val furnitureName = getString(furnitureData.furnitureName)
                    val itemAlreadyAddedToCart = cartList.any {
                        it.furniture == furnitureData
                    }
                    if(!itemAlreadyAddedToCart){
                        cartList.add(CartModel(furnitureData, 1, false))
                        generalFunctions.handlerCartMessage(furnitureName, R.string.added_cart)
                    }else{
                        generalFunctions.handlerCartMessage(furnitureName, R.string.already_added_cart)
                    }
                }
                generalFunctions.clearSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                generalFunctions.saveFurnitureSharedPreferencesArray(cartList, ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                favoriteAdapter.getFavoriteList(favoriteList)
                checkedFurnitureList.clear()
                handlerFields()
            }
        }
    }

    private fun initFavoriteListAdapter() {
        favoriteAdapter =
            FavoriteAdapter(requireContext(), object : FavoriteAdapter.OnItemClickListener {
                override fun onItemClickListener(furnitureData: FurnitureModel, position: Int) {
                    val furnitureName = getString(furnitureData.furnitureName)
                    val cartList: ArrayList<CartModel> =
                        generalFunctions.getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                            ?: ArrayList()
                    val itemAlreadyAddedToCart = cartList.any {
                        it.furniture == furnitureData
                    }
                    if (!itemAlreadyAddedToCart) {
                        cartList.add(CartModel(furnitureData, 1, false))
                        generalFunctions.clearSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                        generalFunctions.saveFurnitureSharedPreferencesArray(
                            cartList,
                            ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey
                        )
                        generalFunctions.handlerCartMessage(furnitureName, R.string.added_cart)
                    } else {
                        generalFunctions.handlerCartMessage(furnitureName, R.string.already_added_cart)
                    }
                }

                override fun onCheckedListener(
                    furnitureData: FurnitureModel,
                    isChecked: Boolean,
                    position: Int
                ) {
                    if (isChecked) {
                        checkedFurnitureList.add(furnitureData)
                        checkedFurniturePrice += furnitureData.furniturePrice
                    } else {
                        checkedFurnitureList.remove(furnitureData)
                        checkedFurniturePrice -= furnitureData.furniturePrice
                    }
                    handlerFields()
                }
            })
        favoriteAdapter.getFavoriteList(favoriteList)
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriteRecyclerView.adapter = favoriteAdapter
    }

    private fun initClicks() {
        binding.buttonDelete.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)
        binding.buttonDelete.setOnClickListener(this)
        binding.buttonAddToCart.setOnClickListener(this)
    }

    private fun handlerFields() {
        binding.cardViewButtonDelete.isVisible = checkedFurnitureList.isNotEmpty()
        binding.buttonAddToCart.isVisible = checkedFurnitureList.isNotEmpty()
        binding.emptyFavoriteWarning.isVisible = favoriteList.isEmpty()
        val checkedFurnitureFormatted = String.format("%.2f", checkedFurniturePrice)
        val stringBuilder = StringBuilder()
            .append(requireContext().getString(R.string.add_to_cart))
            .append(requireContext().getString(R.string.space))
            .append(getString(R.string.pipe))
            .append(requireContext().getString(R.string.space))
            .append(requireContext().getString(R.string.currency_sign))
            .append(checkedFurnitureFormatted)
        binding.buttonAddToCart.text = stringBuilder.toString()
    }
}