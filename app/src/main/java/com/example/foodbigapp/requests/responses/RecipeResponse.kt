package com.example.foodbigapp.requests.responses

import com.example.foodbigapp.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RecipeResponse(
    @SerializedName("recipe")
    @Expose
    var recipe: Recipe? = null
)