package com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/*Data class to get the list of all the repositories of the given user from the API.

name is a String type parameter which cannot be null.
description is a String type parameter which is nullable as this field is optional.
updated_at is a String type parameter which represents time when the repository was last updated.
stargazers_count is an Int type parameter which represents total number of times this repository is starred.
forks is an Int type parameter which represents total number of times this repository is forked.
*/
@Parcelize
@Serializable
data class UserRepositories(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("stargazers_count") val stargazers_count: Int = 0,
    @SerializedName("forks") val forks: Int = 0,
    @SerializedName("total_forks") var total_forks: Int = 0,
    @SerializedName("html_url") val html_url: String,
    @SerializedName("language") val language: String?
) : Parcelable
