package com.mdoc.smartone_git_demo.presentation

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.domain.models.GitAccountDetails
import com.mdoc.smartone_git_demo.domain.models.Repositories
import com.mdoc.smartone_git_demo.domain.states.GetUserDetailsState
import com.mdoc.smartone_git_demo.domain.states.GetUserRepositoryState
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import com.mdoc.smartone_git_demo.utils.CoroutineRule
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

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        runTest {
            Mockito.`when`(getUserDetailsUseCase.invoke(anyString())).thenReturn(
                Resource.success(GitAccountDetails(null, "TusharGogna", "www.test.com"))
            )

            Mockito.`when`(getUserRepositoriesUseCase.invoke(anyString())).thenReturn(
                Resource.success(
                    listOf(
                        Repositories(
                            "Blackboard",
                            "",
                            "",
                            0,
                            0,
                            0,
                            "",
                            null
                        )
                    )
                )
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
    fun `The user details are coming for the given string`() = runTest {
        viewModel.getGitUser(anyString())
        val userDetails = viewModel.userDetailsState.value
        if (userDetails is GetUserDetailsState.OnUserData) {
            Assert.assertEquals("TusharGogna", userDetails.userDetails.login)
        } else if (userDetails is GetUserDetailsState.OnError) {
            Assert.assertEquals("", userDetails.error.name)
        } else if (userDetails is GetUserDetailsState.IsLoading) {
            Assert.assertEquals(
                GetUserDetailsState.IsLoading,
                userDetails
            )
        }
    }

    @Test
    fun `The user repositories are coming for the given string`() = runTest {
        viewModel.getUserRepository(anyString())
        val userRepositories = viewModel.userRepository.value
        if (userRepositories is GetUserRepositoryState.OnUserRepository) {
            Assert.assertEquals("Blackboard", userRepositories.userRepos.get(0).name)
        } else if (userRepositories is GetUserRepositoryState.OnError) {
            Assert.assertEquals("", userRepositories.error.name)
        } else if (userRepositories is GetUserRepositoryState.IsLoading) {
            Assert.assertEquals(
                GetUserRepositoryState.IsLoading,
                userRepositories
            )
        }
    }
}