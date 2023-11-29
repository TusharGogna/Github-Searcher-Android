package com.mdoc.smartone_git_demo.presentation.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun convertDate(currentDate: String): String {
    val parser = SimpleDateFormat("yyyy-M-d'T'HH:mm:ss'Z'")
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
    return parser.parse(currentDate)
        ?.let { formatter.format(it) } ?: currentDate
}