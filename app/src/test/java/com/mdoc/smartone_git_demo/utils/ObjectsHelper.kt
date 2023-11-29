package com.mdoc.smartone_git_demo.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mdoc.smartone_git_demo.data.remote.GitApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

object ObjectsHelper {
    private val mockWebServer: MockWebServer = MockWebServer().apply {
        start(8080)
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getGitApiObject(): GitApi {
        val client = OkHttpClient.Builder().build()
        val contentType = "application/json".toMediaType()
        val jsonConverter = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }
        return Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .client(client).addConverterFactory(jsonConverter.asConverterFactory(contentType))
            .build().create(GitApi::class.java)
    }

    fun getMockWebServerObject(): MockWebServer {
        return mockWebServer
    }
}