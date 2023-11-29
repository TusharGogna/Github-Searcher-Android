package com.mdoc.smartone_git_demo.data.remote

import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {

    @GET("users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: String): Response<UserDetails>

    @GET("users/{userId}/repos")
    suspend fun getUserRepositories(@Path("userId") userId: String): Response<List<UserRepositories>>
}