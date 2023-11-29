package com.mdoc.smartone_git_demo.presentation.screen_userdetails

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.mdoc.smartone_git_demo.R
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import com.mdoc.smartone_git_demo.presentation.MainActivity
import com.mdoc.smartone_git_demo.presentation.UserDetailsViewModel
import com.mdoc.smartone_git_demo.presentation.ui.theme.SmartOne_Git_DemoTheme
import com.mdoc.smartone_git_demo.presentation.utils.SetupNavigationGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DetailsScreenTest {
    private lateinit var viewModel: UserDetailsViewModel

    @Inject
    lateinit var repository: UserDetailsRepository

    @Inject
    lateinit var dispatcher: CoroutineDispatcher

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        val getSpecificGitUser = GetSpecificGitUser(repository)
        val getUserRepositories = GetUserRepositories(repository)
        viewModel = UserDetailsViewModel(getSpecificGitUser, getUserRepositories, dispatcher)
    }


    @Test
    fun test_searchButtonClickWithNoTextShowsError() {
        composeTestRule.activity.setContent {
            SmartOne_Git_DemoTheme {
                val navController = rememberNavController()
                DetailsScreen(navController, viewModel, Modifier)
            }
        }

        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("Empty string is not allowed.").assertIsDisplayed()
    }

    @Test
    fun test_textShowsAsEnteredWithKeypad() {
        composeTestRule.activity.setContent {
            SmartOne_Git_DemoTheme {
                val navController = rememberNavController()
                DetailsScreen(navController, viewModel, Modifier)
            }
        }
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.search_text_field_tag))
            .performTextInput(composeTestRule.activity.getString(R.string.default_userId))
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.search_text_field_tag))
            .assert(hasText(composeTestRule.activity.getString(R.string.default_userId)))
    }

    @Test
    fun test_buttonClickedWithTextShowsNoError() {
        composeTestRule.activity.setContent {
            SmartOne_Git_DemoTheme {
                val navController = rememberNavController()
                DetailsScreen(navController, viewModel, Modifier)
            }
        }
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.search_text_field_tag))
            .performTextInput(composeTestRule.activity.getString(R.string.default_userId))
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithText("Empty string is not allowed.").assertDoesNotExist()

    }


    @Test
    fun test_buttonClickedWithTextShowsLoader() {
        composeTestRule.activity.setContent {
            SmartOne_Git_DemoTheme {
                val navController = rememberNavController()
                DetailsScreen(navController, viewModel, Modifier)
            }
        }
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.search_text_field_tag))
            .performTextInput(composeTestRule.activity.getString(R.string.default_userId))
        composeTestRule.onNodeWithText("Search").performClick()
        composeTestRule.onNodeWithTag(composeTestRule.activity.getString(R.string.loader_tag))
            .assertIsDisplayed()
    }

}