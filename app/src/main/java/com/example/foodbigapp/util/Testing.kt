package com.example.foodbigapp.util

import android.util.Log
import com.example.foodbigapp.models.Recipe

class Testing {

    companion object{
        fun printRecipes(recipes : MutableList<Recipe>, tag:String) {
                for (i in recipes) {
                    Log.d(tag, "" + i.title)
            }
        }
    }

}