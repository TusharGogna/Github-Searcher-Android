package com.mdoc.smartone_git_demo.presentation.utils

sealed class NavigationScreenRoutes(val route: String){
    object Home: NavigationScreenRoutes("home_screen")
    object Details: NavigationScreenRoutes("details_screen")
}
