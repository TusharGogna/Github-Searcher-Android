package com.mdoc.smartone_git_demo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdoc.smartone_git_demo.domain.states.GetUserDetailsState
import com.mdoc.smartone_git_demo.domain.states.GetUserRepositoryState
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import com.mdoc.smartone_git_demo.domain.usecases.utils.ErrorTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val getSpecificGitUser: GetSpecificGitUser,
    private val getUserRepositories: GetUserRepositories,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userDetailsState =
        MutableStateFlow<GetUserDetailsState>(GetUserDetailsState.IsLoading)
    val userDetailsState = _userDetailsState.asStateFlow()

    private val _userRepositoryState =
        MutableStateFlow<GetUserRepositoryState>(GetUserRepositoryState.IsLoading)
    val userRepository = _userRepositoryState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        if (throwable is UnknownHostException) {
            viewModelScope.launch {
                _userRepositoryState.emit(
                    GetUserRepositoryState
                        .OnError(ErrorTypes.NETWORK_ERROR, throwable.message)
                )
                GetUserDetailsState
                    .OnError(ErrorTypes.NETWORK_ERROR, throwable.message)
            }
        }
    }

    fun getGitUser(input: String) {
        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            getSpecificGitUser.invoke(input).let { userDetailsResponse ->
                userDetailsResponse.data?.let { userDetails ->
                    GetUserDetailsState.OnUserData(userDetails)
                }?.let { userDetailsStateOnSuccess ->
                    _userDetailsState.emit(userDetailsStateOnSuccess)
                } ?: userDetailsResponse.message?.let {
                    _userDetailsState.emit(
                        GetUserDetailsState
                            .OnError(ErrorTypes.NETWORK_ERROR, it)
                    )
                }
            } ?: _userDetailsState.emit(
                GetUserDetailsState
                    .OnError(ErrorTypes.NO_USER_FOUND)
            )
        }
    }

    fun getUserRepository(input: String) {
        viewModelScope.launch(dispatcher + coroutineExceptionHandler) {
            getUserRepositories.invoke(input).let { items ->
                items.data?.let { repositories ->
                    if (repositories.isEmpty()) {
                        _userRepositoryState.emit(
                            GetUserRepositoryState
                                .OnError(ErrorTypes.NO_REPOSITORIES_FOUND)
                        )
                    } else {
                        _userRepositoryState.emit(
                            GetUserRepositoryState.OnUserRepository(
                                repositories
                            )
                        )
                    }
                } ?: items.message?.let {
                    _userRepositoryState.emit(
                        GetUserRepositoryState
                            .OnError(ErrorTypes.NETWORK_ERROR, it)
                    )
                }
            } ?: _userRepositoryState.emit(
                GetUserRepositoryState
                    .OnError(ErrorTypes.NO_REPOSITORIES_FOUND)
            )
        }
    }
}