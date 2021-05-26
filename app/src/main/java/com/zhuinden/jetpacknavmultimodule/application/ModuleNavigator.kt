package com.zhuinden.jetpacknavmultimodule.application

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.zhuinden.jetpacknavmultimodule.NavigationRootDirections
import com.zhuinden.jetpacknavmultimodule.R
import com.zhuinden.jetpacknavmultimodule.feature_splash.SplashActions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ModuleNavigator @Inject constructor(
    private val navController: NavController,
): SplashActions {
    override fun navigateToMain(someArg: String,) {
        navController.navigate(NavigationRootDirections.actionSplashToMain(someArg))
    }

    @Module
    @InstallIn(ActivityComponent::class)
    object NavControllerModule {
        @Provides
        fun navController(activity: FragmentActivity): NavController {
            return NavHostFragment.findNavController(activity.supportFragmentManager.findFragmentById(R.id.nav_host)!!)
        }
    }

    @Module
    @InstallIn(ActivityComponent::class)
    abstract class SplashModule {
        @Binds
        abstract fun splash(moduleNavigator: ModuleNavigator): SplashActions
    }
}