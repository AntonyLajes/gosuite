package com.nomargin.gosuite.ui.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.FragmentFurnitureBinding
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.GeneralFunctions
import com.nomargin.gosuite.util.models.CartModel
import com.nomargin.gosuite.util.models.FurnitureModel

class FurnitureFragment : Fragment(), View.OnClickListener {

    private lateinit var furnitureData: FurnitureModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var generalFunctions: GeneralFunctions
    private var _binding: FragmentFurnitureBinding? = null
    private val binding: FragmentFurnitureBinding get() = _binding!!
    private val navigationArguments: FurnitureFragmentArgs by navArgs()
    private var isFavorite: Boolean = false
    private lateinit var furnitureName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFurnitureBinding.inflate(inflater)
        generalFunctions = GeneralFunctions(requireActivity())
        furnitureData = navigationArguments.furnitureData
        furnitureName = requireContext().getString(furnitureData.furnitureName)
        sharedPreferences = requireActivity().getSharedPreferences(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesGeneralKey, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
        isFavorite = isFavorite()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadFurnitureData()
        initClicks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonBack.id -> {
                findNavController().popBackStack()
            }
            binding.buttonFavorite.id -> {
                val favoriteList: ArrayList<FurnitureModel> = generalFunctions.getFurnitureSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey) ?: ArrayList()
                isFavorite = if(isFavorite){
                    favoriteList.remove(furnitureData)
                    generalFunctions.clearSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey)
                    generalFunctions.saveFurnitureSharedPreferencesArray(favoriteList, ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey)
                    binding.buttonFavorite.setImageResource(R.drawable.ic_favorite_border)
                    generalFunctions.handlerFavoriteMessage(furnitureName, R.string.removed_favorite)
                    false
                }else{
                    favoriteList.add(furnitureData)
                    generalFunctions.saveFurnitureSharedPreferencesArray(favoriteList, ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey)
                    binding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                    generalFunctions.handlerFavoriteMessage(furnitureName, R.string.added_favorite)
                    true
                }
            }
            binding.buttonAddToCart.id -> {
                val cartList: ArrayList<CartModel> = generalFunctions.getCartSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey) ?: ArrayList()
                val itemAlreadyAddedToCart = cartList.any{
                    it.furniture == furnitureData
                }
                if(!itemAlreadyAddedToCart){
                    cartList.add(CartModel(furnitureData, 1, false))
                    generalFunctions.clearSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                    generalFunctions.saveFurnitureSharedPreferencesArray(cartList, ApplicationConstants.SharedPreferencesKeys.sharedPreferencesCartKey)
                    generalFunctions.handlerCartMessage(furnitureName, R.string.added_cart)
                }else{
                    generalFunctions.handlerCartMessage(furnitureName, R.string.already_added_cart)
                }
            }
        }
    }

    private fun initClicks(){
        binding.buttonBack.setOnClickListener(this)
        binding.buttonFavorite.setOnClickListener(this)
        binding.buttonAddToCart.setOnClickListener(this)
    }

    private fun loadFurnitureData(){
        val stringBuilder = StringBuilder()
            .append(requireContext().getString(R.string.add_to_cart))
            .append(requireContext().getString(R.string.space))
            .append(getString(R.string.pipe))
            .append(requireContext().getString(R.string.space))
            .append(requireContext().getString(R.string.currency_sign))
            .append(furnitureData.furniturePrice)

        binding.furnitureName.text = requireContext().getString(furnitureData.furnitureName)
        binding.furnitureCategory.text = furnitureData.furnitureCategory[1]
        binding.furnitureDescription.text = requireContext().getString(furnitureData.furnitureDescription)
        binding.buttonAddToCart.text = stringBuilder.toString()
        binding.furnitureRate.text = furnitureData.furnitureRate.toString()
        binding.furnitureImage.setImageResource(furnitureData.furnitureImage)
    }

    private fun isFavorite(): Boolean{
        val favoriteList: ArrayList<FurnitureModel> = generalFunctions.getFurnitureSharedPreferencesArray(ApplicationConstants.SharedPreferencesKeys.sharedPreferencesFavoritesKey) ?: ArrayList()
        for(furniture in favoriteList){
            if(furniture == furnitureData){
                binding.buttonFavorite.setImageResource(R.drawable.ic_favorite)
                return true
            }
        }
        return false
    }

}