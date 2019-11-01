package com.example.foodbigapp.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.foodbigapp.AppExecutors
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.requests.responses.RecipeSearchResponse
import com.example.foodbigapp.util.Constants
import retrofit2.Call
import java.util.concurrent.TimeUnit

class RecipeApiClient private constructor() {

    var mRecipes: MutableLiveData<MutableList<Recipe>> = MutableLiveData()
    private var mRetrieveRecipeRunnable: RetrieveRecipesRunnable? = null

    companion object {
        private val LOCK = Any()
        private var instance: RecipeApiClient? = null

        fun getInstance(): RecipeApiClient = instance ?: synchronized(LOCK) {
            instance ?: RecipeApiClient().also { instance = it }
        }
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null
        }

        mRetrieveRecipeRunnable = RetrieveRecipesRunnable(mRecipes, query, pageNumber)

        mRetrieveRecipeRunnable?.let {
            val handler = AppExecutors.getInstance().mNetworkIO.submit(it)
            AppExecutors.getInstance().mNetworkIO.schedule({
                // let the user know its timed out
                handler.cancel(true)
            }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
        }
    }

    class RetrieveRecipesRunnable(
        private var mRecipes: MutableLiveData<MutableList<Recipe>>, private var query: String, private var pageNumber: Int) : Runnable {
        private var cancelRequest = false

        override fun run() {
            val response = getRecipes(query, pageNumber).execute()
            if (cancelRequest) {
                return
            }
            if (response.isSuccessful) {
                val list: MutableList<Recipe>? = response.body()?.recipes
                if (pageNumber == 1) {
                    mRecipes.postValue(list)
                } else {
                    val currentRecipes: MutableList<Recipe>? = mRecipes.value
                    list?.let {
                        currentRecipes?.addAll(it)
                        mRecipes.postValue(currentRecipes)
                    }
                }
            } else {
                val error = response.errorBody()?.string()
                Log.e("myapp", "Error is: $error")
                mRecipes.postValue(null)
            }

        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return RecipeApiFactory.getInstance().getApiService().searchRecipe(
                Constants.API_KEY, query, pageNumber.toString()
            )
        }

        private fun cancelRequest() {
            Log.d("myapp", "Cancel request")
            cancelRequest = true
        }

    }

}
