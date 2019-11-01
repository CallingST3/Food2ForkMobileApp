package com.example.foodbigapp.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("publisher")
    @Expose
    var publisher: String? = null,
    @SerializedName("f2f_url")
    @Expose
    var f2fUrl: String? = null,
    @SerializedName("ingredients")
    @Expose
    var ingredients: List<String>? = null,
    @SerializedName("source_url")
    @Expose
    var sourceUrl: String? = null,
    @SerializedName("recipe_id")
    @Expose
    var recipeId: String? = null,
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null,
    @SerializedName("social_rank")
    @Expose
    var socialRank: Double = 0.toDouble(),
    @SerializedName("publisher_url")
    @Expose
    var publisherUrl: String? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

}