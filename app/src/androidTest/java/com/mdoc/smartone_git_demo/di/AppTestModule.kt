package com.mdoc.smartone_git_demo.di

import android.content.Context
import com.mdoc.smartone_git_demo.data.FakeUserDetailsRepository
import com.mdoc.smartone_git_demo.domain.UserDetailsRepository
import com.mdoc.smartone_git_demo.domain.usecases.userdetails.GetSpecificGitUser
import com.mdoc.smartone_git_demo.domain.usecases.userrepository.GetUserRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
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
        return FakeUserDetailsRepository()
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