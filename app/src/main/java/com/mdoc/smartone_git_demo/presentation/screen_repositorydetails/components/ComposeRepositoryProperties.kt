package com.mdoc.smartone_git_demo.presentation.screen_repositorydetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.core.GitApplication
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import com.mdoc.smartone_git_demo.presentation.utils.SpecificRepositoryItems


@Composable
fun LoadRepositoryProperties(userRepositories: UserRepositories) {

    val repositoryData by remember {
        mutableStateOf(
            listOf(
                SpecificRepositoryItems(
                    "${userRepositories.total_forks}",
                    R.drawable.total_fork,
                    true
                ),
                SpecificRepositoryItems(
                    "Stars: ${userRepositories.stargazers_count}",
                    R.drawable.star,
                    false
                ),
                SpecificRepositoryItems(
                    "Forks: ${userRepositories.forks}",
                    R.drawable.fork,
                    false
                ),
                SpecificRepositoryItems(
                    "Language: ${userRepositories.language ?: "N/A"}",
                    R.drawable.language,
                    false
                )
            )
        )
    }

    var resourceId by remember {
        mutableIntStateOf(R.drawable.fork)
    }

    LazyRow(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(repositoryData) {
            if (it.isTotalFork) {
                var color = Color.Unspecified
                if (it.itemName.toInt() >= GitApplication.FORKS_CONDITION_COUNT) {
                    resourceId = R.drawable.total_fork
                    color = Color.Yellow
                }
                ElevatedCard(
                    modifier = Modifier
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            painterResource(resourceId),
                            contentDescription = stringResource(R.string.star_icon),
                            modifier = Modifier.size(22.dp),
                            tint = if (it.itemName.toInt() >= GitApplication.FORKS_CONDITION_COUNT)
                                Color.Unspecified
                            else
                                LocalContentColor.current
                        )
                        Text(
                            text = "Total Forks: ${it.itemName}",
                            fontSize = 14.sp,
                            color = color
                        )
                    }
                }
            } else {
                ElevatedCard(
                    modifier = Modifier
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            painterResource(it.icon),
                            contentDescription = stringResource(R.string.star_icon),
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = it.itemName,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}