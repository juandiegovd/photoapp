package com.haystack.news.photoapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val outputDateFormat = SimpleDateFormat("MMMM dd yyyy", Locale.getDefault())

    fun formatDate(inputDateString: String): String {
        val inputDate: Date? = inputDateFormat.parse(inputDateString)
        return outputDateFormat.format(inputDate!!)
    }
}