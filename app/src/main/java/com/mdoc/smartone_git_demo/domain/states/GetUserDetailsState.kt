package com.mdoc.smartone_git_demo.domain.states

import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.usecases.utils.ErrorTypes

sealed class GetUserDetailsState {
    // Used when the data is incoming and has not yet been received
    data object IsLoading : GetUserDetailsState()

    // Used when the data has been fetched
    class OnUserData(val userDetails: UserDetails) : GetUserDetailsState()

    // Used when there is some error
    class OnError(val error: ErrorTypes, val msg: String? = null) : GetUserDetailsState()

}
