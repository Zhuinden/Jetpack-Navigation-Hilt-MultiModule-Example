package com.zhuinden.jetpacknavmultimodule.feature_splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.splash_fragment) {
    @Inject
    lateinit var splashActions: SplashActions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            delay(1000L)
            splashActions.navigateToMain("Blah")
        }
    }
}