package com.example.foodbigapp

import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.foodbigapp.util.Testing
import com.example.foodbigapp.viewmodels.RecipeListViewModel

class RecipeListActivity : BaseActivity() {

    private val TAG = "myapp"
    private var mRecipeListViewModel:RecipeListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel::class.java)

        subscribeObservers()
        findViewById<Button>(R.id.test).setOnClickListener {
            testRetrofitRequest()
        }
    }

    private fun subscribeObservers() {
        mRecipeListViewModel?.getRecipes()?.observe(this, Observer {
            if (it != null) {
                Testing.printRecipes(it, TAG)
            }
        })
    }

    private fun searchRecipesApi(query:String, pageNumber:Int) {
        mRecipeListViewModel?.searchRecipesApi(query, pageNumber)
    }

    private fun testRetrofitRequest() {
        searchRecipesApi("chicken breasts", 1)
    }

}
