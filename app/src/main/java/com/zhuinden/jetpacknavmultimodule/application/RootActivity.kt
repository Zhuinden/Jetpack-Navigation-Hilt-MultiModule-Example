package com.zhuinden.jetpacknavmultimodule.application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.zhuinden.jetpacknavmultimodule.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
    }
}