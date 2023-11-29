package com.mdoc.smartone_git_demo.domain.usecases.userrepository

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories

class GetUserRepositories(private val repository: UserDetailsRepository) {

    suspend operator fun invoke(userId: String): Resource<List<UserRepositories>?> {
        return repository.getUserRepositories(userId)
    }
}