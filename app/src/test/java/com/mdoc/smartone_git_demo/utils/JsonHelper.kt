package com.mdoc.smartone_git_demo.utils

import java.io.InputStreamReader

object JsonHelper {

    fun readFileResource(fileName: String): String {
        val inputStream = JsonHelper::class.java.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}