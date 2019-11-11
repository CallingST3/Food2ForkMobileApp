package com.example.foodbigapp

import android.annotation.SuppressLint
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun setContentView(layoutResID: Int) {
        val constraintLayout: ConstraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activityContent)
        layoutInflater.inflate(layoutResID, frameLayout, true)

        super.setContentView(layoutResID)
    }



}
