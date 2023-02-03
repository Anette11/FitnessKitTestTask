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

fun createDuration(
    from: String,
    to: String,
    defaultValue: String
): String {
    try {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateFrom = simpleDateFormat.parse(from)
        val dateTo = simpleDateFormat.parse(to)
        if (dateFrom == null || dateTo == null) return defaultValue
        if (dateFrom.after(dateTo)) return defaultValue

        var difference = dateTo.time - dateFrom.time

        val secondsInMilliseconds = 1000L
        val minutesInMilliseconds = secondsInMilliseconds * 60
        val hoursInMilliseconds = minutesInMilliseconds * 60
        val daysInMilliseconds = hoursInMilliseconds * 24

        difference %= daysInMilliseconds

        val hours = difference / hoursInMilliseconds
        difference %= hoursInMilliseconds

        val minutes = difference / minutesInMilliseconds
        difference %= minutesInMilliseconds

        return "${hours}ч.${minutes}мин."

    } catch (e: ParseException) {
        return defaultValue
    }
}
