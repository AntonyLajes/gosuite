package com.nomargin.gosuite.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomargin.gosuite.databinding.FragmentSearchBinding
import com.nomargin.gosuite.ui.view.adapters.*
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.GeneralFunctions
import com.nomargin.gosuite.util.general.OnItemClickListener
import com.nomargin.gosuite.util.models.CartModel
import com.nomargin.gosuite.util.models.CategoryModel
import com.nomargin.gosuite.util.models.FurnitureModel

class SearchFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSearchBinding? = null
    private var filterList: List<CategoryModel> = listOf()
    private val binding: FragmentSearchBinding get() = _binding!!
    private val furnitureList: MutableList<FurnitureModel> = mutableListOf()
    private val selectedCategoriesList: MutableList<CategoryModel> = mutableListOf(ApplicationConstants.CategoriesList.categoriesList[0])
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var generalFunctions: GeneralFunctions


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        generalFunctions = GeneralFunctions(requireActivity())
        initFilterRecyclerView()
        initProductsRecyclerView()
        initClicks()
        handlerFields()
        handlerSearchView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonFilter.id -> {
                binding.filterRecyclerView.isVisible = !binding.filterRecyclerView.isVisible
            }
        }
    }

    private fun handlerSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                applySearchFilter(newText)
                return true
            }
        })
    }

    private fun applySearchFilter(query: String?) {
        furnitureList.clear()
        for (furniture in ApplicationConstants.FurnitureList.furnitureList) {
            if (getString(furniture.furnitureName).lowercase().contains(query!!)) {
                for (category in selectedCategoriesList) {
                    if (furniture.furnitureCategory.contains(category.categoryName)) {
                        if (furniture !in furnitureList) {
                            furnitureList.add(furniture)
                        }
                    }
                }
            }
        }
        searchAdapter.getProductsList(furnitureList)
    }

    private fun initProductsRecyclerView() {
        searchAdapter = SearchAdapter(requireContext(), object : OnItemClickListener {
            override fun <T> onItemClickListener(mode: Int, item: T, position: Int) {
                handlerMode(mode, item as FurnitureModel)
            }

            override fun <T> onItemCheckedListener(isChecked: Boolean, item: T, position: Int) {

            }
        })
        searchAdapter.getProductsList(ApplicationConstants.FurnitureList.furnitureList)
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter = searchAdapter
    }

    private fun initFilterRecyclerView() {
        filterList = ApplicationConstants.CategoriesList.categoriesList
        filterAdapter = FilterAdapter(filterList, object : OnItemClickListener {
            override fun <T> onItemClickListener(mode: Int, item: T, position: Int) {}

            override fun <T> onItemCheckedListener(isChecked: Boolean, item: T, position: Int) {
                if (isChecked) {
                    selectedCategoriesList.add(item as CategoryModel)
                } else {
                    selectedCategoriesList.remove(item as CategoryModel)
                }
                applySearchFilter(binding.searchView.query.toString())
            }
        })
        binding.filterRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filterRecyclerView.adapter = filterAdapter
    }

    private fun handlerFields() {
        binding.filterRecyclerView.isVisible = false
        binding.productsRecyclerView.isVisible = furnitureList.isEmpty()
        binding.emptySearchWarning.isVisible = furnitureList.isNotEmpty()
    }

    private fun handlerMode(mode: Int, furniture: FurnitureModel) {
        when (mode) {
            ApplicationConstants.Mode.goToCartMode -> {
                generalFunctions.goToCart(
                    ApplicationConstants.Mode.goToCartMode,
                    CartModel(furniture, 1, false)
                )
            }
            ApplicationConstants.Mode.goToDetailsMode -> {
                goToFurnitureDetails(furniture)
            }
        }
    }

    private fun goToFurnitureDetails(furniture: FurnitureModel) {
        findNavController().navigate(
            SearchFragmentDirections.actionNavSearchToFurnitureFragment(
                furniture
            )
        )
    }

    private fun initClicks() {
        binding.buttonFilter.setOnClickListener(this)
    }
}