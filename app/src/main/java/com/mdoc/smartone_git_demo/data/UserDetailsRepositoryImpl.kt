package com.mdoc.smartone_git_demo.data

import android.content.Context
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.data.remote.GitApi
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(val api: GitApi, val context: Context?) :
    UserDetailsRepository {

    override suspend fun getUserDetails(userId: String): Resource<UserDetails?> {
        return try {
            val response = api.getUserDetails(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(context!!.getString(R.string.error_unknown_issue), null)
            } else {
                Resource.error(context!!.getString(R.string.error_unknown_issue), null)
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString(), null)
        }
    }

    override suspend fun getUserRepositories(userId: String): Resource<List<UserRepositories>?> {
        return try {
            val response = api.getUserRepositories(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(context!!.getString(R.string.error_unknown_issue), null)
            } else {
                Resource.error(context!!.getString(R.string.error_unknown_issue), null)
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString(), null)
        }
    }
}