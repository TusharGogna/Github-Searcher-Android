package com.mdoc.smartone_git_demo.di

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mdoc.smartone_git_demo.core.GitApplication
import com.mdoc.smartone_git_demo.data.FakeUserDetailsRepositoryImpl
import com.mdoc.smartone_git_demo.data.UserDetailsRepositoryImpl
import com.mdoc.smartone_git_demo.data.remote.GitApi
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object AppTestModule {
    @Singleton
    @Provides
    fun provideContext(application: HiltTestApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun providesUserRepository(): UserDetailsRepository {
        return FakeUserDetailsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher {
        return StandardTestDispatcher()
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