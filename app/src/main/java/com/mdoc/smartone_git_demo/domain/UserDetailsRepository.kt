package com.mdoc.smartone_git_demo.domain

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories

interface UserDetailsRepository {
    //Remote Repository

    //Suspend function to call getUserDetails method to fetch user details from API
    suspend fun getUserDetails(userId: String): Resource<UserDetails?>

    //Suspend function to call getUserRepositories method to fetch user repo list from API
    suspend fun getUserRepositories(userId: String): Resource<List<UserRepositories>?>
}