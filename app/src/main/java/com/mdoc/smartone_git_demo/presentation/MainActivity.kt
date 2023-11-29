package com.mdoc.smartone_git_demo.presentation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.presentation.ui.components.ToolBar
import com.mdoc.smartone_git_demo.presentation.ui.theme.SmartOne_Git_DemoTheme
import com.mdoc.smartone_git_demo.presentation.utils.SetupNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Loading The Splash Screen Before Rendering MainActivity Screen
        setupSplash()

        setContent {
            SmartOne_Git_DemoTheme {
                // Initializing NavController in order to load the required Compose Screen
                navController = rememberNavController()

                // Initializing the ViewModel by Injecting it using Dagger
                userDetailsViewModel = hiltViewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        ToolBar(getString(R.string.title_home_page))
                    }) { scaffoldPadding ->

                    // Calling the Navigation Graph in order to get
                    // the Start Screen and load the same.
                    SetupNavigationGraph(
                        navHostController = navController,
                        userDetailsViewModel,
                        Modifier.absolutePadding(
                            0.dp,
                            scaffoldPadding.calculateTopPadding(),
                            0.dp,
                            0.dp
                        )
                    )
                }

                // Using a default value so that we see some data when we start the application.
                val defaultInput = getString(R.string.default_userId)

                // Method to fetch user details via /user/{userId}
                userDetailsViewModel.getGitUser(defaultInput)

                // Method to fetch user repositories via /user/{userId}/repo
                userDetailsViewModel.getUserRepository(defaultInput)
            }
        }
    }

    //Method to load the Splash Screen before loading the Landing Activity.
    private fun setupSplash() {
        // The code in apply scope is optional. I used it just to add a small animation to the icon.
        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }
                zoomX.start()
                zoomY.start()
            }
        }
    }
}


