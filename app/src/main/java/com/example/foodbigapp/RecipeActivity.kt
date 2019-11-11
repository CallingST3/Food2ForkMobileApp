package com.example.foodbigapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.viewmodels.RecipeViewModel
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlin.math.roundToInt

class RecipeActivity : BaseActivity() {

    private lateinit var mRecipeViewModel: RecipeViewModel
    private lateinit var mScrollView:ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        mScrollView = findViewById(R.id.parent)

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        subscribeObservers()
        getIncomingIntent()

    }

    private fun subscribeObservers() {
        mRecipeViewModel.getRecipe().observe(this,
            Observer<Recipe> {
                it?.let {
                    if (it.recipeId == mRecipeViewModel.mRecipeId) {
                        setRecipeProperties(it)
                        mRecipeViewModel.mDidRetrieveRecipe = true
                    }
                }
            })
        mRecipeViewModel.isRecipeRequestTimeout().observe(this, Observer {
            if (it && !mRecipeViewModel.mDidRetrieveRecipe) {
                displayErrorScreen("Error retrieving data. Check network connection.")
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun displayErrorScreen(errorMessage:String) {
        recipe_title.text = "Error retrieveing recipe..."
        recipe_social_score.text = ""
        val textView = TextView(this)
        if (errorMessage.isNotEmpty()) {
            textView.text = errorMessage
        } else {
            textView.text = "Unknown error"
        }
        textView.textSize = 15.toFloat()
        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

        Glide.with(this).setDefaultRequestOptions(requestOptions)
            .load(R.drawable.ic_launcher_background)
            .into(recipe_image)

        showParent()
    }


    private fun getIncomingIntent() {
        if (intent.hasExtra("recipe")) {
            val recipe = intent.getParcelableExtra<Recipe>("recipe")
            recipe?.recipeId?.let { mRecipeViewModel.searchRecipeById(it) }
        }
    }

    private fun setRecipeProperties(recipe:Recipe) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)

        Glide.with(this).setDefaultRequestOptions(requestOptions)
            .load(recipe.imageUrl)
            .into(recipe_image)
        recipe_title.text = recipe.title
        recipe_social_score.text = recipe.socialRank.roundToInt().toString()

        ingredients_container.removeAllViews()
        recipe.ingredients?.let {
            for (ingredient in it) {
                val textView = TextView(this)
                textView.text = ingredient
                textView.textSize = 15.toFloat()
                textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                ingredients_container.addView(textView)
            }
        }
        showParent()

    }
    private fun showParent() {
        mScrollView.visibility = View.VISIBLE
    }

}