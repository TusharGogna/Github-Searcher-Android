package com.mdoc.smartone_git_demo.domain.usecases.userdetails

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails

class GetSpecificGitUser(private val repository: UserDetailsRepository) {

    suspend operator fun invoke(userId: String): Resource<UserDetails?> {
        return repository.getUserDetails(userId)
    }
}