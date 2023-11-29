package com.mdoc.smartone_git_demo.presentation

import androidx.lifecycle.viewModelScope
import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.models.GitAccountDetails
import com.mdoc.smartone_git_demo.domain.models.Repositories
import com.mdoc.smartone_git_demo.domain.states.GetUserDetailsState
import com.mdoc.smartone_git_demo.domain.states.GetUserRepositoryState
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import com.mdoc.smartone_git_demo.utils.CoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserDetailsViewModelTest {

    private lateinit var viewModel: UserDetailsViewModel

    @Mock
    private lateinit var getUserDetailsUseCase: GetSpecificGitUser

    @Mock
    private lateinit var getUserRepositoriesUseCase: GetUserRepositories

    @get:Rule
    val coroutineRule = CoroutineRule()

    private val PASS_STRING = "PASS"
    private val FAIL_STRING = "FAIL"
    private val SUCCESS_USER_DATA = GitAccountDetails(null, "TusharGogna", "www.test.com")
    private val ERROR_USER_RESPONSE = Resource.error("Unexpected Error Occurred", null)

    private val SUCCESS_REPOSITORIES_DATA = listOf(
        Repositories(
            "Blackboard",
            "",
            "",
            0,
            0,
            5200,
            "",
            null
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        runTest {
            Mockito.`when`(getUserDetailsUseCase.invoke(PASS_STRING)).thenReturn(
                Resource.success(SUCCESS_USER_DATA)
            )
            Mockito.`when`(getUserDetailsUseCase.invoke(FAIL_STRING)).thenReturn(
                ERROR_USER_RESPONSE
            )

            Mockito.`when`(getUserRepositoriesUseCase.invoke(PASS_STRING)).thenReturn(
                Resource.success(
                    SUCCESS_REPOSITORIES_DATA
                )
            )
            Mockito.`when`(getUserRepositoriesUseCase.invoke(FAIL_STRING)).thenReturn(
                ERROR_USER_RESPONSE
            )

            viewModel =
                UserDetailsViewModel(
                    getUserDetailsUseCase,
                    getUserRepositoriesUseCase,
                    coroutineRule.testDispatcher
                )
        }
    }

    @Test
    fun `the initial stage of GetUserDetailsState should be idle`() = runTest {
        viewModel.getGitUser(PASS_STRING)
        val userDetails = viewModel.userDetailsState.value

        Assert.assertEquals(
            GetUserDetailsState.IsIdle,
            (userDetails as GetUserDetailsState.IsIdle)
        )
    }

    @Test
    fun `the getGitUser method should be loaded successfully and emit userDetails`() = runTest {
        viewModel.getGitUser(PASS_STRING)
        testScheduler.advanceUntilIdle()

        val userDetails = viewModel.userDetailsState.value

        Assert.assertEquals(
            SUCCESS_USER_DATA.login,
            (userDetails as GetUserDetailsState.OnUserData).userDetails.login
        )
        Assert.assertEquals(
            SUCCESS_USER_DATA.name,
            userDetails.userDetails.name
        )
        Assert.assertEquals(
            SUCCESS_USER_DATA.avatarUrl,
            userDetails.userDetails.avatarUrl
        )
    }

    @Test
    fun `the getGitUser method should throw an error`() = runTest {
        viewModel.getGitUser(FAIL_STRING)
        testScheduler.advanceUntilIdle()

        val userDetails = viewModel.userDetailsState.value

        Assert.assertEquals(
            ERROR_USER_RESPONSE.message,
            (userDetails as GetUserDetailsState.OnError).msg
        )
    }

    @Test
    fun `the initial stage of GetUserRepositoryState should be idle`() = runTest {
        viewModel.getUserRepository(PASS_STRING)
        val userDetails = viewModel.userRepository.value

        Assert.assertEquals(
            GetUserRepositoryState.IsIdle,
            (userDetails as GetUserRepositoryState.IsIdle)
        )
    }

    @Test
    fun `the getUserRepository method should be loaded successfully and emit list of repositories`() =
        runTest {
            viewModel.getUserRepository(PASS_STRING)
            testScheduler.advanceUntilIdle()

            val repositories = viewModel.userRepository.value

            Assert.assertEquals(
                SUCCESS_REPOSITORIES_DATA.size,
                (repositories as GetUserRepositoryState.OnUserRepository).userRepos.size
            )
            Assert.assertEquals(
                SUCCESS_REPOSITORIES_DATA[0].name,
                repositories.userRepos[0].name
            )
            Assert.assertEquals(
                SUCCESS_REPOSITORIES_DATA[0].totalForks,
                repositories.userRepos[0].totalForks
            )
        }

    @Test
    fun `the getUserRepository method should throw an error`() = runTest {
        viewModel.getUserRepository(FAIL_STRING)
        testScheduler.advanceUntilIdle()

        val repositories = viewModel.userRepository.value

        Assert.assertEquals(
            ERROR_USER_RESPONSE.message,
            (repositories as GetUserRepositoryState.OnError).msg
        )
    }
}