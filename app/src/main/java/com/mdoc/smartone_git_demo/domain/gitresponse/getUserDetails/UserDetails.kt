package com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*Data class to get User Github account specific details from the API.

name is a String type parameter which is nullable as name can be null.
login is a String type parameter which cannot be null.
avatar_url is a String type parameter which is nullable as the use might not have any image set.
*/
@Serializable
data class UserDetails(
    @SerializedName("user_name") val name: String?,
    @SerializedName("user_login") val login: String,
    @SerializedName("avatar_url") val avatar_url: String?
)
