package com.hythes.jsonplaceholder.application.util

import java.text.SimpleDateFormat
import java.util.*

enum class StatusView {
    LOADING, EMPTY, ERROR, INFORMATION
}

const val LOCALE_IN = "in"
const val LOCALE_ID = "ID"
const val SDF_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
const val SDF_UTC2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val SDF_TYPE = "dd MMMM yyyy HH:mm"

fun formatDateCustomToCustom(stringDate: String, firstFormat: String, formatResult:String): String{
    val sdf = SimpleDateFormat(firstFormat, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val parseDate = sdf.parse(stringDate)

    val sdf2 = SimpleDateFormat(formatResult, Locale(LOCALE_IN, LOCALE_ID))
    return sdf2.format(parseDate)
}