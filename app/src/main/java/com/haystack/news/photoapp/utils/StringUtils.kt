package com.haystack.news.photoapp.utils

object StringUtils {
    fun formatString(str: String, cant: Int): String{
        if (str.length > cant) return str.substring(0, cant)
        return str
    }
}