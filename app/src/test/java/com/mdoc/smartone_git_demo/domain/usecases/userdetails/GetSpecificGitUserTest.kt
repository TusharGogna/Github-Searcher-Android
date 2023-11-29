package com.mdoc.smartone_git_demo.domain.usecases.userdetails

import com.mdoc.smartone_git_demo.data.FakeUserDetailsRepositoryImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetSpecificGitUserTest {

    private lateinit var repository: FakeUserDetailsRepositoryImpl
    private lateinit var getSpecificGitUser: GetSpecificGitUser

    @Before
    fun setUp() {
        repository = FakeUserDetailsRepositoryImpl()
        getSpecificGitUser = GetSpecificGitUser(repository)
    }

    @Test
    fun `Test that response for getUserDetails is successful`() = runTest {
        repository.shouldThrowErrorResponse(false)
        val response = getSpecificGitUser.invoke("Test")

        assertEquals(true, response.data != null)
        assertEquals("TusharGogna", response.data!!.login)
    }
}