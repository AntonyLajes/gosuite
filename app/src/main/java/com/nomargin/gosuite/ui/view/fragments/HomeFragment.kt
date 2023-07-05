package com.nomargin.gosuite.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomargin.gosuite.R
import com.nomargin.gosuite.databinding.FragmentHomeBinding
import com.nomargin.gosuite.ui.view.adapters.BestSellerAdapter
import com.nomargin.gosuite.ui.view.adapters.CategoriesAdapter
import com.nomargin.gosuite.ui.view.adapters.MainShowcaseAdapter
import com.nomargin.gosuite.util.constants.ApplicationConstants
import com.nomargin.gosuite.util.general.GeneralFunctions
import com.nomargin.gosuite.util.general.OnItemClickListener
import com.nomargin.gosuite.util.models.CartModel
import com.nomargin.gosuite.util.models.CategoryModel
import com.nomargin.gosuite.util.models.FurnitureModel

class HomeFragment : Fragment() {

    private lateinit var mainShowcaseAdapter: MainShowcaseAdapter
    private lateinit var generalFunctions: GeneralFunctions
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val categoriesList: ArrayList<CategoryModel> = arrayListOf(ApplicationConstants.CategoriesList.categoriesList[0])
    private val furnitureList: ArrayList<FurnitureModel> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        generalFunctions = GeneralFunctions(requireActivity())
        navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        mockedData()
        initCategoriesRecyclerView()
        initMainShowcaseRecyclerView()
        initBestSellerRecyclerView()
    }

    private fun initCategoriesRecyclerView() {
        val categoriesAdapter = CategoriesAdapter(
            requireContext(),
            categoriesList,
            object : CategoriesAdapter.OnItemClickListener {
                override fun onItemClickListener(filteredCategory: CategoryModel, position: Int) {
                    val filteredFurniture = arrayListOf<FurnitureModel>()
                    filteredFurniture.clear()
                    for (furniture in furnitureList) {
                        for (furnitureCategory in furniture.furnitureCategory) {
                            if (furnitureCategory == filteredCategory.categoryName) {
                                filteredFurniture.add(furniture)
                            }
                        }
                    }
                    mainShowcaseAdapter.getFurnitureList(filteredFurniture)
                }

            })
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecyclerView.adapter = categoriesAdapter
    }

    private fun initMainShowcaseRecyclerView() {
        mainShowcaseAdapter = MainShowcaseAdapter(requireContext(), object : OnItemClickListener {
            override fun <T> onItemClickListener(mode: Int, item: T, position: Int) {
                handlerMode(mode, item as FurnitureModel)
            }

            override fun <T> onItemCheckedListener(isChecked: Boolean, item: T, position: Int) {}
        })
        mainShowcaseAdapter.getFurnitureList(furnitureList)
        binding.furnituresRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.furnituresRecyclerView.adapter = mainShowcaseAdapter
    }

    private fun initBestSellerRecyclerView() {
        val bestSellerFurniture = arrayListOf<FurnitureModel>()
        bestSellerFurniture.clear()
        for (furniture in furnitureList) {
            if (furniture.isBestSeller) {
                bestSellerFurniture.add(furniture)
            }
        }
        val bestSellerAdapter = BestSellerAdapter(requireContext(), bestSellerFurniture,
            object : OnItemClickListener {
                override fun <T> onItemClickListener(
                    mode: Int,
                    item: T,
                    position: Int
                ) {
                    handlerMode(mode, item as FurnitureModel)
                }

                override fun <T> onItemCheckedListener(isChecked: Boolean, item: T, position: Int) {}

            })
        binding.bestSellerRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.bestSellerRecyclerView.adapter = bestSellerAdapter
    }

    private fun goToFurnitureDetails(furniture: FurnitureModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavHomeToFurnitureFragment(
                furniture
            )
        )
    }

    private fun handlerMode(mode: Int, furniture: FurnitureModel){
        when (mode) {
            ApplicationConstants.Mode.goToCartMode -> {
                generalFunctions.goToCart(ApplicationConstants.Mode.goToCartMode, CartModel(furniture, 1, false))
            }
            ApplicationConstants.Mode.goToDetailsMode -> {
                goToFurnitureDetails(furniture)
            }
        }
    }

    private fun mockedData() {
        categoriesList.clear()
        val mockedCategoriesList = ApplicationConstants.CategoriesList.categoriesList
        for(category in mockedCategoriesList){
            categoriesList.add(category)
        }
        furnitureList.clear()
        val mockedFurnitureList = ApplicationConstants.FurnitureList.furnitureList
        for(furniture in mockedFurnitureList){
            furnitureList.add(furniture)
        }
    }
}
