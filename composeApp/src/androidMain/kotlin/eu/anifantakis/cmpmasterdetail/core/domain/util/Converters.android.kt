package eu.anifantakis.cmpmasterdetail.core.domain.util

import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

actual fun String.toLocalDate(format: String): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ofPattern(format)
        val javaLocalDate = java.time.LocalDate.parse(this, formatter)
        LocalDate(javaLocalDate.year, javaLocalDate.monthValue, javaLocalDate.dayOfMonth)
    } catch (e: DateTimeParseException) {
        null
    }
}

actual fun LocalDate.toFormattedString(format: String): String {
    val javaLocalDate = java.time.LocalDate.of(year, monthNumber, dayOfMonth)
    val formatter = DateTimeFormatter.ofPattern(format)
    return javaLocalDate.format(formatter)
}