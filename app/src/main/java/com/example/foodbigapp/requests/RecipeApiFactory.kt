package com.example.foodbigapp.requests

import com.example.foodbigapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiFactory private constructor(
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()
) {

    companion object {
        private val LOCK = Any()
        private val RECIPE_API_FACTORY: RecipeApiFactory? = null
        fun getInstance(): RecipeApiFactory = RECIPE_API_FACTORY ?: synchronized(LOCK) {
            RECIPE_API_FACTORY ?: RecipeApiFactory()
        }
    }

    fun getApiService():RecipeApiService {
        return  retrofit.create(RecipeApiService::class.java)
    }

}