package com.mdoc.smartone_git_demo.domain.models


data class Repositories(
    val name: String,
    val description: String?,
    val updatedAt: String,
    val stargazersCount: Int = 0,
    val forks: Int = 0,
    var totalForks: Int = 0,
    val htmlUrl: String,
    val language: String?
)


