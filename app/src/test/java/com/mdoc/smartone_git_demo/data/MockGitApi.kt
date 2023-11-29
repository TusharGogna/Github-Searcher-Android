package com.mdoc.smartone_git_demo.data

import com.mdoc.smartone_git_demo.data.remote.GitApi
import com.mdoc.smartone_git_demo.utils.JsonHelper
import com.mdoc.smartone_git_demo.utils.ObjectsHelper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MockGitApi {
    private lateinit var gitApiService: GitApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        val objHelper = ObjectsHelper
        mockWebServer = objHelper.getMockWebServerObject()
        gitApiService = objHelper.getGitApiObject()
    }

    @Test
    fun GetUserDetails_Successfully() = runTest {
        val content = JsonHelper.readFileResource("/user.json")
        val mockResponse = MockResponse()
            .setBody(content)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        val response = gitApiService.getUserDetails("Test")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() != null)
        Assert.assertEquals("TusharGogna", response.body()!!.login)
        Assert.assertEquals(null, response.body()!!.name)
        Assert.assertEquals("https://www.testimage.com", response.body()!!.avatar_url)

    }

    @Test
    fun GetUserDetails_Failed() = runTest {
        val mockResponse = MockResponse()
            .setBody("Unknown Error Occurred!")
            .setResponseCode(400)
        mockWebServer.enqueue(mockResponse)
        val response = gitApiService.getUserDetails("Test")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() == null)
        Assert.assertEquals(false, response.isSuccessful)
    }

    @Test
    fun getUserRepositories_Successfully() = runTest {
        val content = JsonHelper.readFileResource("/repositories.json")
        val mockResponse = MockResponse()
            .setBody(content)
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)
        val response = gitApiService.getUserRepositories("Test")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() != null)
        Assert.assertEquals(2, response.body()!!.size)
        Assert.assertEquals("Backboard", response.body()!![0].name)
        Assert.assertEquals(
            "https://github.com/TusharGogna/Backboard",
            response.body()!![0].html_url
        )
        Assert.assertEquals(
            "A motion-driven animation framework for Android.",
            response.body()!![0].description
        )
        Assert.assertEquals(
            "2021-09-19T06:27:58Z",
            response.body()!![0].updated_at
        )
        Assert.assertEquals(0, response.body()!![0].forks)
        Assert.assertEquals(0, response.body()!![0].stargazers_count)
        Assert.assertEquals(0, response.body()!![0].total_forks)
        Assert.assertEquals(null, response.body()!![0].language)
    }


    @Test
    fun getUserRepositories_Failed() = runTest {
        val mockResponse = MockResponse()
            .setBody("Unknown Error Occurred!")
            .setResponseCode(400)
        mockWebServer.enqueue(mockResponse)
        val response = gitApiService.getUserRepositories("Test")
        mockWebServer.takeRequest()

        Assert.assertEquals(true, response.body() == null)
        Assert.assertEquals(false, response.isSuccessful)
    }
}