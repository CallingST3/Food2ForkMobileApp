package com.example.foodbigapp.requests.responses

import com.example.foodbigapp.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RecipeSearchResponse(
    @SerializedName("count")
    @Expose
    var count: Int = 0,
    @SerializedName("recipes")
    @Expose
    var recipes: MutableList<Recipe>? = null
)