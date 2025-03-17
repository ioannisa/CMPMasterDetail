package eu.anifantakis.cmpmasterdetail.core.domain.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

expect fun String.toLocalDate(format: String = "yyyy-MM-dd"): LocalDate?
expect fun LocalDate.toFormattedString(format: String = "yyyy-MM-dd"): String


fun Long.toDateTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toLocalDateTime(timeZone) // Or use system default with TimeZone.currentSystemDefault()
}

fun LocalDateTime.toLong(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    return this.toInstant(timeZone).toEpochMilliseconds()
}

fun Long.toDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(timeZone)
        .date
}

fun LocalDate.toLong(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long {
    val startOfDay = LocalDateTime(this, LocalTime(0, 0))
    return startOfDay.toInstant(timeZone).toEpochMilliseconds()
}

fun secondsToTime(secondsOfDay: Int): LocalTime {
    return LocalTime.fromSecondOfDay(secondsOfDay)
}

fun LocalTime.toSeconds(): Int {
    return this.toSecondOfDay()
}

fun timestampToTimeComponent(timestamp: Long): LocalTime {
    return Instant.fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.UTC)
        .time
}