package com.example.foodbigapp

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors private constructor() {


    val mNetworkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3)


    companion object {
        private val LOCK = Any()
        private var instance: AppExecutors? = null

        fun getInstance(): AppExecutors = instance ?: synchronized(LOCK) {
            instance ?: AppExecutors().also { instance = it }
        }
    }




}