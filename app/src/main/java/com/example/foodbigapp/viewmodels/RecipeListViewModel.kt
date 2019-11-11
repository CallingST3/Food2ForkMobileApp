package com.example.foodbigapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {

    private var mRecipeRepository: RecipeRepository = RecipeRepository.getInstance()
    var mIsViewingRecipes = false
    var mIsPerformingQuery = false


    fun getRecipes(): MutableLiveData<MutableList<Recipe>> {
        return mRecipeRepository.mRecipeApiClient.mRecipes
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        mIsViewingRecipes = true
        mIsPerformingQuery = true
        mRecipeRepository.searchRecipesApi(query, pageNumber)
    }

    fun searchNextPage() {
        if (!mIsPerformingQuery && mIsViewingRecipes) {
            mRecipeRepository.searchNextPage()
        }
    }

    fun onBackPressed(): Boolean {
        if (mIsPerformingQuery) {
            // cancel the query
            mRecipeRepository.cancelRequest()
            mIsPerformingQuery = false
        }
        if (mIsViewingRecipes) {
            mIsViewingRecipes = false
            return false
        }
        return true
    }


}