package com.example.foodbigapp.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbigapp.R
import com.example.foodbigapp.models.Recipe
import com.example.foodbigapp.util.Constants
import kotlinx.android.synthetic.main.layout_recipe_list_item.view.*
import kotlin.math.roundToInt

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(recipe : Recipe, listener : (Recipe) -> Unit){

        if (itemViewType == Constants.RECIPE_TYPE) {
            val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
            Glide.with(itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.imageUrl)
                .into(itemView.recipe_image)

            itemView.recipe_title.text = recipe.title
            itemView.recipe_publisher.text = recipe.publisher
            itemView.recipe_social_score.text = (recipe.socialRank.roundToInt()).toString()
            itemView.setOnClickListener{listener(recipe)}
        }



    }


}