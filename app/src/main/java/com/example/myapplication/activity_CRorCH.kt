package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class activity_CRorCH : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crorch)
        val extraID = intent.getStringExtra(MainActivity.EXTRA_KEY)
    }
}