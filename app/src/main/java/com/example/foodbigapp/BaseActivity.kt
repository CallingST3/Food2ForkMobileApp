package com.example.foodbigapp

import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        var constraintLayout: ConstraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        var frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activityContent)
        layoutInflater.inflate(layoutResID, frameLayout, true)

        super.setContentView(layoutResID)
    }



}
