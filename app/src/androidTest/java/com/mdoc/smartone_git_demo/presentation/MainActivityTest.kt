package com.mdoc.smartone_git_demo.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import com.mdoc.smartone_git_demo.presentation.ui.theme.SmartOne_Git_DemoTheme
import com.mdoc.smartone_git_demo.presentation.utils.SetupNavigationGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.mdoc.smartone_git_demo.R

@HiltAndroidTest
class MainActivityTest {

    private lateinit var viewModel: UserDetailsViewModel

    @Inject
    lateinit var repository: UserDetailsRepository

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        val getSpecificGitUser = GetSpecificGitUser(repository)
        val getUserRepositories = GetUserRepositories(repository)
        viewModel = UserDetailsViewModel(getSpecificGitUser, getUserRepositories, dispatcher)
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun test_detailPageLoadedSuccessfullyWithSearchButtonDisplayed() {
        composeTestRule.activity.setContent {
            SmartOne_Git_DemoTheme {
                val navController = rememberNavController()
                SetupNavigationGraph(navController, viewModel, Modifier)
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.button_search)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.txt_idle_repositories))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.txt_idle_user_details))
            .assertIsDisplayed()
    }

}