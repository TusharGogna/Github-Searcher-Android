package com.mdoc.smartone_git_demo.di

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mdoc.smartone_git_demo.core.GitApplication.Companion.BASE_URL
import com.mdoc.smartone_git_demo.data.UserDetailsRepositoryImpl
import com.mdoc.smartone_git_demo.data.remote.GitApi
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideGitApi(): GitApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
            .create(GitApi::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepository(api: GitApi, context: Context): UserDetailsRepository {
        return UserDetailsRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun providesGetSpecificGitUserUseCase(repository: UserDetailsRepository): GetSpecificGitUser {
        return GetSpecificGitUser(repository)
    }

    @Provides
    @Singleton
    fun providesGetUserRepositoryUseCase(repository: UserDetailsRepository): GetUserRepositories {
        return GetUserRepositories(repository)
    }
}