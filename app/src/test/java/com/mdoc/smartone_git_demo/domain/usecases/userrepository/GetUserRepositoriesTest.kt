package com.mdoc.smartone_git_demo.domain.usecases.userrepository

import com.mdoc.smartone_git_demo.data.FakeUserDetailsRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUserRepositoriesTest {

    private lateinit var repository: FakeUserDetailsRepositoryImpl
    private lateinit var getUserRepositories: GetUserRepositories

    @Before
    fun setUp() {
        repository = FakeUserDetailsRepositoryImpl()
        getUserRepositories = GetUserRepositories(repository)
    }

    @Test
    fun `Test that response for getUserRepositories is successful`() = runTest {
        repository.shouldThrowErrorResponse(false)
        val response = getUserRepositories.invoke("Test")

        assertEquals(true, response.data != null)
        assertEquals(2, response.data!!.size)
        assertEquals("Backboard", response.data!![0].name)
    }
}