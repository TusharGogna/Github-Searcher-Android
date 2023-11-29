package com.mdoc.smartone_git_demo.presentation.screen_userdetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.domain.models.GitAccountDetails
import com.mdoc.smartone_git_demo.domain.states.GetUserDetailsState
import com.mdoc.smartone_git_demo.domain.usecases.utils.ErrorTypes

@Composable
fun UserDetailsCard(userDetailState: GetUserDetailsState) {
    var state by remember {
        mutableStateOf<GetUserDetailsState>(GetUserDetailsState.IsLoading)
    }
    val modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    state = userDetailState
    when (state) {
        GetUserDetailsState.IsLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .testTag(stringResource(R.string.loader_tag))
                    .width(44.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary
            )
        }

        is GetUserDetailsState.OnError -> {
            val errorText: String = when ((state as GetUserDetailsState.OnError).error) {
                ErrorTypes.NETWORK_ERROR -> (state as GetUserDetailsState.OnError).msg
                    ?: stringResource(R.string.empty_string)

                ErrorTypes.NO_REPOSITORIES_FOUND -> stringResource(R.string.error_no_repository)

                ErrorTypes.NO_USER_FOUND -> stringResource(R.string.error_no_user)
            }
            Text(
                modifier = modifier,
                text = errorText,
                textAlign = TextAlign.Center
            )
        }

        is GetUserDetailsState.OnUserData -> {
            ElevatedCard(
                modifier = modifier
                    .shadow(10.dp)
                    .testTag(stringResource(R.string.user_card_test_tag)),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                LoadUserDetailsCard(
                    modifier = modifier,
                    state = (state as GetUserDetailsState.OnUserData).userDetails
                )
            }
        }

        GetUserDetailsState.IsIdle -> {
            Text(
                modifier = modifier,
                text = stringResource(R.string.txt_idle_user_details),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LoadUserDetailsCard(modifier: Modifier, state: GitAccountDetails) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.avatarUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.git),
            contentDescription = stringResource(R.string.user_image_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "User: ${(state.name ?: state.login)}"
        )
    }
}