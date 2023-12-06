package com.mdoc.smartone_git_demo.domain.usecases.userrepository

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.core.Status
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.toRepositories
import com.mdoc.smartone_git_demo.domain.models.Repositories

class GetUserRepositories(private val repository: UserDetailsRepository) {

    suspend operator fun invoke(userId: String): Resource<List<Repositories>?> {
        val repositories = repository.getUserRepositories(userId).data?.asSequence()?.map {
            it.toRepositories()
        }?.toList()
        return if (repositories != null) {
             if(repositories.isNotEmpty()){
                Resource(Status.SUCCESS, repositories, null)
            }else{
                Resource(Status.ERROR, repositories, null)
            }
        }else Resource(Status.ERROR, repositories, null)
    }
}