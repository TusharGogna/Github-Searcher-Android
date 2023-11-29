package com.mdoc.smartone_git_demo.presentation.screen_userdetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import com.mdoc.smartone_git_demo.domain.states.GetUserRepositoryState
import com.mdoc.smartone_git_demo.domain.usecases.utils.ErrorTypes
import com.mdoc.smartone_git_demo.presentation.utils.NavigationScreenRoutes

@Composable
fun UserRepositoryListing(
    userRepositoryState: GetUserRepositoryState,
    navController: NavHostController
) {
    var state by remember {
        mutableStateOf<GetUserRepositoryState>(GetUserRepositoryState.IsLoading)
    }
    val modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    state = userRepositoryState
    when (state) {
        is GetUserRepositoryState.IsLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .padding(18.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(text = stringResource(R.string.loading))
            }
        }

        is GetUserRepositoryState.OnError -> {
            val errorText: String = when ((state as GetUserRepositoryState.OnError).error) {
                ErrorTypes.NETWORK_ERROR -> (state as GetUserRepositoryState.OnError).msg
                    ?: stringResource(R.string.error_network_issue)

                ErrorTypes.NO_REPOSITORIES_FOUND -> stringResource(R.string.error_no_repository)

                ErrorTypes.NO_USER_FOUND -> stringResource(R.string.error_no_user)
            }
            Text(
                modifier = modifier,
                text = errorText,
                textAlign = TextAlign.Center
            )
        }

        is GetUserRepositoryState.OnUserRepository -> {
            LoadRepositories(
                (state as GetUserRepositoryState.OnUserRepository).userRepos,
                navController
            )
        }
    }
}

@Composable
private fun LoadRepositories(userRepos: List<UserRepositories>, navController: NavHostController) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 8.dp),
        text = stringResource(R.string.title_repositories),
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(userRepos) { repositoryItem ->
            val forks = userRepos.map {
                it.forks
            }
            repositoryItem.totalForks = forks.sum()
            RepositoryItem(repositoryItem, navController)
        }
    }
}


@Composable
private fun RepositoryItem(
    repositoryItem: UserRepositories,
    navController: NavHostController
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(4.dp)
            .clickable {

                val gson: Gson = GsonBuilder().create()

                val repositoryItemJson = gson.toJson(repositoryItem)
                navController.navigate(
                    NavigationScreenRoutes.Details.route +
                            "?repositoryJson=$repositoryItemJson"
                )
            },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = repositoryItem.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = repositoryItem.description ?: "N/A",
                fontSize = 18.sp
            )
        }
    }
}