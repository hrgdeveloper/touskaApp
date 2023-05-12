package com.example.data.utils

import saman.zamani.persiandate.PersianDate
import java.text.SimpleDateFormat
import java.util.*

fun String.convertGregorianToPersian():String {
    val gregorianDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


    val gregorianCalendar = Calendar.getInstance()
    gregorianCalendar.time = gregorianDateFormat.parse(this)
    val persianDate = PersianDate(gregorianCalendar.timeInMillis)

    // Access individual components of the Persian date
    val year = persianDate.shYear
    val month = persianDate.shMonth
    val day = persianDate.shDay

    // Format the Persian date as a string
    val persianDateStr = String.format("%04d-%02d-%02d", year, month, day)


    return persianDateStr
}