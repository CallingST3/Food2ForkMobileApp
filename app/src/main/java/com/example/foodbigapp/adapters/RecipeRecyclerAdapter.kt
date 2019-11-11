package com.example.foodbigapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbigapp.R
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.util.Constants

class RecipeRecyclerAdapter(private val listener: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var mRecipes: MutableList<Recipe> = mutableListOf()

    fun setRecipes(recipes: MutableList<Recipe>) {
        mRecipes = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            Constants.RECIPE_TYPE -> RecipeViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_recipe_list_item,
                    parent,
                    false
                )
            )
            Constants.LOADING_TYPE -> LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_loading_list_item,
                    parent,
                    false
                )
            )
            Constants.CATEGORY_TYPE -> CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_category_list_item,
                    parent,
                    false
                )
            )
            else -> CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.layout_category_list_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return mRecipes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == Constants.RECIPE_TYPE) {
            (holder as RecipeViewHolder).bind(mRecipes[position], listener)
        } else if (itemViewType == Constants.CATEGORY_TYPE) {
            (holder as CategoryViewHolder).bind(mRecipes[position], listener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (mRecipes[position].title == "LOADING...") {
            Constants.LOADING_TYPE
        } else if(mRecipes[position].socialRank == (-1).toDouble()) {
            Constants.CATEGORY_TYPE
        }else if(position == mRecipes.size - 1 && position != 0 && mRecipes[position].title != "EXHAUSTED...") {
            Constants.LOADING_TYPE
        } else {
            Constants.RECIPE_TYPE
        }
    }

    fun displayLoading() {
        if (!isLoading()) {
            val recipe = Recipe(title = "LOADING...")
            val loadingList = mutableListOf<Recipe>()
            loadingList.add(recipe)
            mRecipes = loadingList
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean {
        mRecipes.let {
            if (mRecipes.size > 0) {
                if (mRecipes.last().title == "LOADING...") {
                    return true
                }
            }
        }
        return false
    }

    fun displaySearchCategories() {
        val caterogiesList = mutableListOf<Recipe>()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            val recipe = Recipe()
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
            recipe.imageUrl = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
            recipe.socialRank = (-1).toDouble()
            caterogiesList.add(recipe)
        }
        mRecipes = caterogiesList
        notifyDataSetChanged()
    }
}