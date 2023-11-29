package com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories

import com.mdoc.smartone_git_demo.domain.models.Repositories
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*Data class to get the list of all the repositories of the given user from the API.

name is a String type parameter which cannot be null.
description is a String type parameter which is nullable as this field is optional.
updated_at is a String type parameter which represents time when the repository was last updated.
stargazers_count is an Int type parameter which represents total number of times this repository is starred.
forks is an Int type parameter which represents total number of times this repository is forked.
*/
@Serializable
data class UserRepositories(
    val name: String,
    val description: String?,
    @SerialName(value = "updated_at")
    val updatedAt: String,
    @SerialName(value = "stargazers_count")
    val stargazersCount: Int = 0,
    val forks: Int = 0,
    var totalForks: Int = 0,
    @SerialName(value = "html_url")
    val htmlUrl: String,
    val language: String?
)

fun UserRepositories.toRepositories() = Repositories(
    name = name,
    description = description,
    updatedAt = updatedAt,
    stargazersCount = stargazersCount,
    forks = forks,
    totalForks = totalForks,
    htmlUrl = htmlUrl,
    language = language
)