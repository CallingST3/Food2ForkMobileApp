package com.example.foodbigapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.repositories.RecipeRepository

class RecipeViewModel : ViewModel() {

    private val mRecipeRepository = RecipeRepository.getInstance()
    var mDidRetrieveRecipe = false
    var mRecipeId:String = ""

    fun getRecipe():MutableLiveData<Recipe> {
        return mRecipeRepository.mRecipeApiClient.mRecipe
    }

    fun searchRecipeById(recipeId:String) {
        mRecipeId = recipeId
        mRecipeRepository.searchRecipeById(recipeId)
    }

    fun isRecipeRequestTimeout():MutableLiveData<Boolean> {
        return mRecipeRepository.isRecipeRequestTimeout()
    }

}