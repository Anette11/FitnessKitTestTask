package com.example.fitnesskittesttask.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.createDateFromString(): Date? {
    if (this == null) return null
    return try {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        simpleDateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun Date?.createStringFromDate(defaultValue: String): String {
    if (this == null) return defaultValue
    return try {
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        simpleDateFormat.format(this)
    } catch (e: ParseException) {
        defaultValue
    }
}