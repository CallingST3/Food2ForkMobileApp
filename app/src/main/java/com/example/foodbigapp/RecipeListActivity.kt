package com.example.foodbigapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbigapp.adapters.RecipeRecyclerAdapter
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.util.Constants
import com.example.foodbigapp.util.Testing
import com.example.foodbigapp.util.VerticalSpacingItemDecorator
import com.example.foodbigapp.viewmodels.RecipeListViewModel

class RecipeListActivity : BaseActivity() {

    private lateinit var mRecipeListViewModel:RecipeListViewModel
    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter:RecipeRecyclerAdapter
    private lateinit var mSearchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        mRecipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel::class.java)
        mRecyclerView = findViewById(R.id.recipeList)
        mSearchView = findViewById(R.id.search_view)

        initRecyclerView()
        subscribeObservers()
        initSearchView()
        if (!mRecipeListViewModel.mIsViewingRecipes) {
            displaySearchCategories()
        }
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    private fun subscribeObservers() {
        mRecipeListViewModel.getRecipes().observe(this, Observer {
            if (it != null) {
                Testing.printRecipes(it, Constants.TAG)
                mRecipeListViewModel.mIsPerformingQuery = false
                mAdapter.setRecipes(it)
            }
        })
    }

    private fun initRecyclerView() {
        mAdapter = RecipeRecyclerAdapter { partItem: Recipe -> recipeItemClicked(partItem)}
        mRecyclerView.adapter = mAdapter
        val itemDecorator = VerticalSpacingItemDecorator(30)
        mRecyclerView.addItemDecoration(itemDecorator)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!mRecyclerView.canScrollVertically(1)) {
                    // search the next page
                    mRecipeListViewModel.searchNextPage()
                }
            }
        })
    }

    private fun recipeItemClicked(recipeItem : Recipe) {
        if (recipeItem.socialRank.toInt() == -1) {
            // category clicked
            mAdapter.displayLoading()
            Log.d(Constants.TAG, recipeItem.title + ", $recipeItem")
            recipeItem.title?.let { mRecipeListViewModel.searchRecipesApi(it, 1) }
            mSearchView.clearFocus()
        } else {
            // recipe clicked
            Log.d(Constants.TAG, recipeItem.toString())
            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("recipe", recipeItem)
            startActivity(intent)
        }
    }

    private fun initSearchView() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter.displayLoading()
                query?.let {
                    mRecipeListViewModel.searchRecipesApi(query, 1)
                    mSearchView.clearFocus()
                    return true
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })
    }

    private fun displaySearchCategories() {
        mRecipeListViewModel.mIsViewingRecipes = false
        mAdapter.displaySearchCategories()
    }

    override fun onBackPressed() {
        if (mRecipeListViewModel.onBackPressed()) {
            super.onBackPressed()
        } else {
            displaySearchCategories()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_categories) {
            displaySearchCategories()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
