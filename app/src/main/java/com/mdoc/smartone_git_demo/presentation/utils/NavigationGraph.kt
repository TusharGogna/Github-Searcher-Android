package com.mdoc.smartone_git_demo.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import com.mdoc.smartone_git_demo.presentation.screen_repositorydetails.RepositoryDetailsScreen
import com.mdoc.smartone_git_demo.presentation.screen_userdetails.DetailsScreen
import com.mdoc.smartone_git_demo.presentation.UserDetailsViewModel

@Composable
fun SetupNavigationGraph(
    navHostController: NavHostController,
    viewModel: ViewModel,
    modifier: Modifier
) {
    NavHost(navController = navHostController, startDestination = NavigationScreenRoutes.Home.route)
    {
        composable(route = NavigationScreenRoutes.Home.route) {
            DetailsScreen(navHostController, viewModel as UserDetailsViewModel, modifier)
        }

        composable(
            route = NavigationScreenRoutes.Details.route +
                    "?repositoryJson={repositoryJson}",
            arguments = listOf(
                navArgument(
                    name = "repositoryJson"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            val gson: Gson = GsonBuilder().create()
            val repositoryJson = it.arguments?.getString("repositoryJson")
            val userRepositoriesObj = gson.fromJson(repositoryJson, UserRepositories::class.java)

            RepositoryDetailsScreen(
                navHostController,
                userRepositoriesObj,
                modifier
            )
        }
    }
}