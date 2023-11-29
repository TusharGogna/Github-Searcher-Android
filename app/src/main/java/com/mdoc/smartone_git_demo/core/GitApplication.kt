package com.mdoc.smartone_git_demo.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitApplication: Application()  {
    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val FORKS_CONDITION_COUNT = 5000
    }
}