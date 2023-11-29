package com.mdoc.smartone_git_demo.presentation.screen_repositorydetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.domain.models.Repositories
import com.mdoc.smartone_git_demo.presentation.screen_repositorydetails.components.LoadRepositoryProperties
import com.mdoc.smartone_git_demo.presentation.utils.convertDate

@Composable
fun RepositoryDetailsScreen(
    navHostController: NavHostController,
    userRepositories: Repositories,
    modifier: Modifier
) {
    var isDateShown by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val uriHandler = LocalUriHandler.current
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            text = userRepositories.name,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        LoadRepositoryProperties(userRepositories)

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.dp, 36.dp, 0.dp, 0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        isDateShown = !isDateShown
                    }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .weight(0.8F)
                        .verticalScroll(rememberScrollState()),
                    text = userRepositories.description
                        ?: stringResource(R.string.no_desc_placeholder),
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                AnimatedVisibility(isDateShown) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(0.1F),
                        text = "Updated at: ${convertDate(userRepositories.updatedAt)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(0.1F),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            uriHandler.openUri(userRepositories.htmlUrl)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = stringResource(R.string.btn_visit_repository))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            navHostController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = stringResource(R.string.btn_go_back))
                    }
                }

            }
        }
    }
}