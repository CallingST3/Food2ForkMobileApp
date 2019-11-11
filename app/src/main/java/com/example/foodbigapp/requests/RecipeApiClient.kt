package com.example.foodbigapp.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.foodbigapp.AppExecutors
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.requests.responses.RecipeResponse
import com.example.foodbigapp.requests.responses.RecipeSearchResponse
import com.example.foodbigapp.util.Constants
import retrofit2.Call
import java.util.concurrent.TimeUnit

class RecipeApiClient private constructor() {

    var mRecipes: MutableLiveData<MutableList<Recipe>> = MutableLiveData()
    var mRecipe : MutableLiveData<Recipe> = MutableLiveData()
    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null
    private var mRetrieveRecipeRunnable : RetrieveRecipeRunnable? = null
    val isRecipeRequestTimeout = MutableLiveData<Boolean>()

    companion object {
        private val LOCK = Any()
        private var instance: RecipeApiClient? = null

        fun getInstance(): RecipeApiClient = instance ?: synchronized(LOCK) {
            instance ?: RecipeApiClient().also { instance = it }
        }
    }

    fun searchRecipesApi(query: String, pageNumber: Int) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null
        }

        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(mRecipes, query, pageNumber)

        mRetrieveRecipesRunnable?.let {
            val handler = AppExecutors.getInstance().mNetworkIO.submit(it)
            AppExecutors.getInstance().mNetworkIO.schedule({
                // let the user know its timed out
                handler.cancel(true)
            }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
        }
    }

    fun searchRecipeById(recipeId:String) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null
        }
        mRetrieveRecipeRunnable = RetrieveRecipeRunnable(recipeId, mRecipe)

        mRetrieveRecipeRunnable?.let {
            val handler = AppExecutors.getInstance().mNetworkIO.submit(it)

            isRecipeRequestTimeout.value = true
            AppExecutors.getInstance().mNetworkIO.schedule({
                // let the user know its timed out
                isRecipeRequestTimeout.postValue(true)
                handler.cancel(true)
            }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)

        }

    }

    class RetrieveRecipesRunnable(
        private var mRecipes: MutableLiveData<MutableList<Recipe>>,
        private var query: String,
        private var pageNumber: Int
    ) : Runnable {
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
                Log.e(Constants.TAG, "Error is: $error")
                mRecipes.postValue(null)
            }
        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return RecipeApiFactory.getInstance().getApiService().searchRecipe(
                Constants.API_KEY, query, pageNumber.toString()
            )
        }

        fun cancelRequest() {
            Log.d("myapp", "canceling request")
            cancelRequest = true
        }

    }

    class RetrieveRecipeRunnable(private val recipeId:String,
                                 private var mRecipe:MutableLiveData<Recipe>) : Runnable {

        private var cancelRequest: Boolean = false

        override fun run() {
            val response = getRecipe(recipeId).execute()
            if (cancelRequest) {
                return
            }
            if (response.isSuccessful) {
                val recipe = response.body()?.recipe
                mRecipe.postValue(recipe)
            } else {
                Log.e(Constants.TAG, response.errorBody().toString())
                mRecipe.postValue(null)
            }
        }

        private fun getRecipe(recipeId:String):Call<RecipeResponse> {
            return RecipeApiFactory.getInstance().getApiService().getRecipe(Constants.API_KEY, recipeId)
        }

        fun cancelRequest() {
            Log.d(Constants.TAG, "canceling request")
            cancelRequest = true
        }

    }


    fun cancelRequest() {
        mRetrieveRecipesRunnable?.let {
            mRetrieveRecipesRunnable?.cancelRequest()
        }
        mRetrieveRecipeRunnable?.let {
            mRetrieveRecipeRunnable?.cancelRequest()
        }
    }

}
