package com.example.foodbigapp.repositories

import com.example.foodbigapp.requests.RecipeApiClient

class RecipeRepository private constructor() {

    var mRecipeApiClient:RecipeApiClient = RecipeApiClient.getInstance()

    companion object {
        private var instance: RecipeRepository? = null
        private val LOCK = Any()

        fun getInstance(): RecipeRepository = instance ?: synchronized(LOCK) {
            instance ?: RecipeRepository().also { instance = it }
        }
    }

    fun searchRecipesApi(query:String, pageNumber:Int) {
        val actualPageNumber = if (pageNumber == 0) 1 else pageNumber
        mRecipeApiClient.searchRecipesApi(query, actualPageNumber)
    }

}