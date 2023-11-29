package com.mdoc.smartone_git_demo.domain.states

import com.mdoc.smartone_git_demo.domain.models.Repositories
import com.mdoc.smartone_git_demo.domain.usecases.utils.ErrorTypes

sealed class GetUserRepositoryState {

    // Used when no action is taken place. Used during the creation time.
    data object IsIdle : GetUserRepositoryState()

    // Used when the data is incoming and has not yet been received
    data object IsLoading : GetUserRepositoryState()

    // Used when the data has been fetched
    class OnUserRepository(val userRepos: List<Repositories>) :
        GetUserRepositoryState()

    // Used when there is some error
    class OnError(val error: ErrorTypes, val msg: String? = null) : GetUserRepositoryState()
}
