package com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails

import com.mdoc.smartone_git_demo.domain.models.GitAccountDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*Data class to get User Github account specific details from the API.

name is a String type parameter which is nullable as name can be null.
login is a String type parameter which cannot be null.
avatar_url is a String type parameter which is nullable as the use might not have any image set.
*/
@Serializable
data class UserDetails(
    val name: String?,
    val login: String,
    @SerialName(value = "avatar_url")
    val avatarUrl: String?
)


fun UserDetails.toAccountDetails() = GitAccountDetails(
    name = name,
    login = login,
    avatarUrl = avatarUrl,
)