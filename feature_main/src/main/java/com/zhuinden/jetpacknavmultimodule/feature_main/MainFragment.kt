package com.zhuinden.jetpacknavmultimodule.feature_main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuinden.jetpacknavmultimodule.feature_main.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.main_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)
        // ...
    }
}