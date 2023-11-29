package com.mdoc.smartone_git_demo.domain.usecases.userdetails

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.core.Status
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.toAccountDetails
import com.mdoc.smartone_git_demo.domain.models.GitAccountDetails

class GetSpecificGitUser(private val repository: UserDetailsRepository) {

    suspend operator fun invoke(userId: String): Resource<GitAccountDetails?> {
        val accountDetails = repository.getUserDetails(userId).data?.toAccountDetails()
        return if (accountDetails != null) {
            Resource(Status.SUCCESS, accountDetails, null)
        } else {
            Resource(Status.ERROR, null, "Unexpected Error")
        }
    }
}