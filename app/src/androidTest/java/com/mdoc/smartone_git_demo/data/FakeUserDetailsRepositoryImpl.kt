package com.mdoc.smartone_git_demo.data

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories

class FakeUserDetailsRepositoryImpl : UserDetailsRepository {

    private var throwError = false

    fun shouldThrowErrorResponse(value: Boolean) {
        throwError = value
    }

    override suspend fun getUserDetails(userId: String): Resource<UserDetails?> {
        return if (throwError) {
            Resource.error("Error Occurred", null)
        } else {
            Resource.success(UserDetails("", "", ""))
        }
    }

    override suspend fun getUserRepositories(userId: String): Resource<List<UserRepositories>?> {
        return if (throwError) {
            Resource.error("Error Occurred", null)
        } else {
            Resource.success(
                listOf(
                    UserRepositories(
                        "Repo 1", "This is repo 1", "2022-11-03",
                        0, 0, 0, "", ""
                    ),
                    UserRepositories(
                        "Repo 2", null, "1994-04-22",
                        0, 0, 5770, "", ""
                    )
                )
            )
        }
    }
}