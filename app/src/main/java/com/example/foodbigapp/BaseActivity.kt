package com.example.foodbigapp

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseActivity : AppCompatActivity() {

    var mProgressBar: ProgressBar? = null

    override fun setContentView(layoutResID: Int) {
        var constraintLayout: ConstraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        var frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activityContent)
        mProgressBar = constraintLayout.findViewById(R.id.progressBar)
        layoutInflater.inflate(layoutResID, frameLayout, true)

        super.setContentView(layoutResID)
    }

    fun showProgressBar(visibility: Boolean) {
        if (visibility) {
            mProgressBar?.visibility = View.VISIBLE
            Log.d("myapp", "turn on")
        } else {
            mProgressBar?.visibility = View.INVISIBLE
            Log.d("myapp", "turn off")
        }
    }

}