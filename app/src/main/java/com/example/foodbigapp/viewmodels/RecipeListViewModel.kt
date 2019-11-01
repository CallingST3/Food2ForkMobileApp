package com.example.foodbigapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {

    private var mRecipeRepository: RecipeRepository=RecipeRepository.getInstance()


    fun getRecipes() : MutableLiveData<MutableList<Recipe>> {
        return mRecipeRepository.mRecipeApiClient.mRecipes
    }

    fun searchRecipesApi(query:String, pageNumber:Int) {
        mRecipeRepository.searchRecipesApi(query, pageNumber)
    }

}