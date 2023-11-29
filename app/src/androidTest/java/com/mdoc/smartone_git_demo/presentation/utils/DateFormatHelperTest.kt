package com.mdoc.smartone_git_demo.presentation.utils

import org.junit.Assert
import org.junit.Test

class DateFormatHelperTest {

    private val givenDate = "2021-09-19T06:27:58Z"
    private val resultingDate = "19.09.2021 06:27"
    @Test
    fun updateTimeFormatShouldBeMoreReadable() {
        val dateFormatHelper = DateFormatHelper()
        val result = dateFormatHelper.convertDate(givenDate)

        Assert.assertEquals(resultingDate, result)
    }
}