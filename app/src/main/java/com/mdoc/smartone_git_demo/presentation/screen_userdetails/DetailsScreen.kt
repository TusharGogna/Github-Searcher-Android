package com.mdoc.smartone_git_demo.presentation.screen_userdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mdoc.smartone_git_demo.presentation.UserDetailsViewModel
import com.mdoc.smartone_git_demo.presentation.screen_userdetails.components.UserDetailsCard
import com.mdoc.smartone_git_demo.presentation.screen_userdetails.components.UserIdInputField
import com.mdoc.smartone_git_demo.presentation.screen_userdetails.components.UserRepositoryListing


@Composable
fun DetailsScreen(
    navController: NavHostController,
    userDetailsViewModel: UserDetailsViewModel,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        UserIdInputField(onSearch = {
            userDetailsViewModel.getGitUser(it)
            userDetailsViewModel.getUserRepository(
                it
            )
        })
        UserDetailsCard(
            userDetailState =
            userDetailsViewModel.userDetailsState.collectAsStateWithLifecycle().value
        )

        UserRepositoryListing(
            userRepositoryState =
            userDetailsViewModel.userRepository.collectAsStateWithLifecycle().value,
            navController
        )
    }
}