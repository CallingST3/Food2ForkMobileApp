package com.example.foodbigapp.util

class Constants {

    companion object{
        const val TAG = "myapp"
        const val BASE_URL = "https://www.food2fork.com/"
        //const val API_KEY = "e36fce8645c0ba6028ce3217ff1e37b1"
        const val API_KEY = "5beedf5ca0e2125d8a3f6bf732095985"
        const val NETWORK_TIMEOUT:Long = 3000
        const val RECIPE_TYPE = 1
        const val LOADING_TYPE = 2
        const val CATEGORY_TYPE = 3

        val DEFAULT_SEARCH_CATEGORIES = arrayOf(
            "Barbeque",
            "Breakfast",
            "Chicken",
            "Beef",
            "Brunch",
            "Dinner",
            "Wine",
            "Italian"
        )

        val DEFAULT_SEARCH_CATEGORY_IMAGES = arrayOf(
            "barbeque",
            "breakfast",
            "chicken",
            "beef",
            "brunch",
            "dinner",
            "wine",
            "italian"
        )
    }

}