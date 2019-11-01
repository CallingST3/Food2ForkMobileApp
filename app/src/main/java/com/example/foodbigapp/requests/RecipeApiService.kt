package com.example.foodbigapp.requests

import com.example.foodbigapp.requests.responses.RecipeResponse
import com.example.foodbigapp.requests.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("api/search")
    fun searchRecipe(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("page") page: String
    ): Call<RecipeSearchResponse>

    @GET("api/get")
    fun getRecipe(
        @Query("key") key: String,
        @Query("rId") recipeId: String
    ): Call<RecipeResponse>

}