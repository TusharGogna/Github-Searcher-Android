package com.mdoc.smartone_git_demo.data

import com.mdoc.smartone_git_demo.core.Resource
import com.mdoc.smartone_git_demo.data.remote.GitApi
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserDetails.UserDetails
import com.mdoc.smartone_git_demo.domain.gitresponse.getUserRepositories.UserRepositories
import com.mdoc.smartone_git_demo.utils.JsonHelper
import com.mdoc.smartone_git_demo.utils.ObjectsHelper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert


class FakeUserDetailsRepositoryImpl : UserDetailsRepository {

    private var throwError = false
    private lateinit var gitApiService: GitApi
    private lateinit var mockWebServer: MockWebServer


    fun shouldThrowErrorResponse(value: Boolean) {
        throwError = value
    }

    override suspend fun getUserDetails(userId: String): Resource<UserDetails?> {
        val objHelper = ObjectsHelper
        mockWebServer = objHelper.getMockWebServerObject()
        gitApiService = objHelper.getGitApiObject()

        return if (throwError) {
            Resource.error("Error Occurred", null)
        } else {
            val content = JsonHelper.readFileResource("/user.json")
            val mockResponse = MockResponse()
                .setBody(content)
                .setResponseCode(200)
            mockWebServer.enqueue(mockResponse)
            val response = gitApiService.getUserDetails("Test")
            mockWebServer.takeRequest()

            Resource.success(response.body())
        }
    }

    override suspend fun getUserRepositories(userId: String): Resource<List<UserRepositories>?> {
        val objHelper = ObjectsHelper
        mockWebServer = objHelper.getMockWebServerObject()
        gitApiService = objHelper.getGitApiObject()

        return if (throwError) {
            Resource.error("Error Occurred", null)
        } else {
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
            Resource.success(response.body())
        }

    }

}