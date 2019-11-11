package com.example.foodbigapp.adapters

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodbigapp.R
import com.example.foodbigapp.models.Recipe
import kotlinx.android.synthetic.main.layout_category_list_item.view.*

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(recipe: Recipe, listener: (Recipe) -> Unit) {
        val requestOptions = RequestOptions().placeholder(R.drawable.ic_launcher_background)
        val packageName = R::class.java.`package`?.name
        val path = Uri.parse("android.resource://${packageName}/drawable/${recipe.imageUrl}")

        Glide.with(itemView.context)
            .setDefaultRequestOptions(requestOptions)
            .load(path)
            .into(itemView.imageViewCategoryImage)

        itemView.textViewCategoryTitle.text = recipe.title
        itemView.setOnClickListener{listener(recipe)}
    }
}