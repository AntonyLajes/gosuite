package com.nomargin.gosuite.util.constants

import com.nomargin.gosuite.R
import com.nomargin.gosuite.util.models.CategoryModel
import com.nomargin.gosuite.util.models.FurnitureModel

class ApplicationConstants private constructor(){

    object Categories {
        const val all = "All"
        const val bedroom = "Bedroom"
        const val livingRoom = "Living Room"
        const val diningRoom = "Dining Room"
        const val office = "Office"
        const val outdoor = "Outdoor"
    }

    object SharedPreferencesKeys {
        const val sharedPreferencesGeneralKey = "FavoritesData"
        const val sharedPreferencesFavoritesKey = "FavoritesFurniture"
        const val sharedPreferencesCartKey = "CartFurniture"
    }

    object Mode {
        const val goToDetailsMode = 1
        const val goToCartMode = 2
        const val cartButtonAddQuantityMode = 3
        const val cartButtonRemoveQuantityMode = 4
        const val cartCheckItemMode = 5
    }

    object CategoriesList {
        val categoriesList: List<CategoryModel> = listOf(
            CategoryModel(
                0,
                Categories.all
            ),
            CategoryModel(
                1,
                Categories.bedroom
            ),
            CategoryModel(
                2,
                Categories.livingRoom
            ),
            CategoryModel(
                3,
                Categories.outdoor
            ),
            CategoryModel(
                4,
                Categories.diningRoom
            ),
            CategoryModel(
                5,
                Categories.office
            )
        )
    }

    object FurnitureList{
        val furnitureList: List<FurnitureModel> = listOf(
            FurnitureModel(
                R.string.bed_name,
                R.string.bed_description,
                655.11F,
                arrayListOf(
                    Categories.all,
                    Categories.bedroom
                ),
                4.8F,
                false,
                R.drawable.bed
            ),
            FurnitureModel(
                R.string.dresser_name,
                R.string.dresser_description,
                399.95F,
                arrayListOf(
                    Categories.all,
                    Categories.bedroom
                ),
                4.7F,
                true,
                R.drawable.dresser
            ),
            FurnitureModel(
                R.string.nightstand_name,
                R.string.nightstand_description,
                101.44F,
                arrayListOf(
                    Categories.all,
                    Categories.bedroom
                ),
                4.9F,
                false,
                R.drawable.nightstand
            ),
            FurnitureModel(
                R.string.sofa_name,
                R.string.sofa_description,
                611.25F,
                arrayListOf(
                    Categories.all,
                    Categories.livingRoom
                ),
                4.8F,
                true,
                R.drawable.sofa
            ),
            FurnitureModel(
                R.string.coffee_table_name,
                R.string.coffee_table_description,
                77.98F,
                arrayListOf(
                    Categories.all,
                    Categories.livingRoom
                ),
                4.9F,
                false,
                R.drawable.coffetable
            ),
            FurnitureModel(
                R.string.tv_stand_name,
                R.string.tv_stand_description,
                187.75F,
                arrayListOf(
                    Categories.all,
                    Categories.livingRoom
                ),
                4.8F,
                false,
                R.drawable.tvstand
            ),
            FurnitureModel(
                R.string.patio_lounge_chair_name,
                R.string.patio_lounge_chair_description,
                249.99F,
                arrayListOf(
                    Categories.all,
                    Categories.outdoor
                ),
                4.7F,
                true,
                R.drawable.patio_lounge_chair
            ),
            FurnitureModel(
                R.string.outdoor_dining_table_name,
                R.string.outdoor_dining_table_description,
                349.99F,
                arrayListOf(
                    Categories.all,
                    Categories.outdoor
                ),
                4.4F,
                false,
                R.drawable.outdoor_dining_table
            ),
            FurnitureModel(
                R.string.garden_bench_name,
                R.string.garden_bench_description,
                179.99F,
                arrayListOf(
                    Categories.all,
                    Categories.outdoor
                ),
                4.6F,
                false,
                R.drawable.garden_bench
            ),
            FurnitureModel(
                R.string.dining_table_set_name,
                R.string.dining_table_set_description,
                799.99F,
                arrayListOf(
                    Categories.all,
                    Categories.diningRoom
                ),
                4.9F,
                false,
                R.drawable.dining_table_set
            ),
            FurnitureModel(
                R.string.sideboard_buffet_name,
                R.string.sideboard_buffet_description,
                499.99F,
                arrayListOf(
                    Categories.all,
                    Categories.diningRoom
                ),
                4.5F,
                false,
                R.drawable.sideboard_buffet
            ),
            FurnitureModel(
                R.string.upholstered_dining_chairs_name,
                R.string.upholstered_dining_chairs_description,
                149.99F,
                arrayListOf(
                    Categories.all,
                    Categories.diningRoom
                ),
                4.6F,
                true,
                R.drawable.upholstered_dining_chairs
            ),
            FurnitureModel(
                R.string.ergonomic_office_chair_name,
                R.string.ergonomic_office_chair_description,
                299.99F,
                arrayListOf(
                    Categories.all,
                    Categories.office
                ),
                4.9F,
                false,
                R.drawable.ergonomic_office_chair
            ),
            FurnitureModel(
                R.string.executive_desk_name,
                R.string.executive_desk_description,
                599.99F,
                arrayListOf(
                    Categories.all,
                    Categories.office
                ),
                4.7F,
                false,
                R.drawable.executive_desk
            ),
            FurnitureModel(
                R.string.bookshelf_with_storage_name,
                R.string.bookshelf_with_storage_description,
                349.99F,
                arrayListOf(
                    Categories.all,
                    Categories.office
                ),
                4.5F,
                false,
                R.drawable.bookshelf_with_storage
            )
        )
    }

}