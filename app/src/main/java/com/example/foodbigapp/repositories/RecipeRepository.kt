package com.example.foodbigapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.foodbigapp.requests.RecipeApiClient

class RecipeRepository private constructor() {

    private var mQuery = ""
    private var mPageNumber = 0

    var mRecipeApiClient:RecipeApiClient = RecipeApiClient.getInstance()

    companion object {
        private var instance: RecipeRepository? = null
        private val LOCK = Any()

        fun getInstance(): RecipeRepository = instance ?: synchronized(LOCK) {
            instance ?: RecipeRepository().also { instance = it }
        }
    }

    fun searchRecipesApi(query:String, pageNumber:Int) {
        mQuery = query
        mPageNumber = pageNumber
        val actualPageNumber = if (pageNumber == 0) 1 else pageNumber
        mRecipeApiClient.searchRecipesApi(query, actualPageNumber)
    }

    fun searchRecipeById(recipeId:String) {
        mRecipeApiClient.searchRecipeById(recipeId)
    }

    fun searchNextPage() {
            searchRecipesApi(mQuery, mPageNumber + 1)
    }

    fun cancelRequest() {
        mRecipeApiClient.cancelRequest()
    }

    fun isRecipeRequestTimeout():MutableLiveData<Boolean> {
        return mRecipeApiClient.isRecipeRequestTimeout
    }


}