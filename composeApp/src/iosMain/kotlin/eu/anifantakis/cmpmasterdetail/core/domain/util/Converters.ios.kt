package eu.anifantakis.cmpmasterdetail.core.domain.util

import kotlinx.datetime.LocalDate
import platform.Foundation.*

actual fun String.toLocalDate(format: String): LocalDate? {
    val dateFormatter = NSDateFormatter().apply {
        dateFormat = format
        locale = NSLocale.localeWithLocaleIdentifier("en_US_POSIX") // For consistent parsing
    }

    val nsDate = dateFormatter.dateFromString(this) ?: return null

    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        nsDate
    )

    return LocalDate(
        components.year.toInt(),
        components.month.toInt(),
        components.day.toInt()
    )
}

actual fun LocalDate.toFormattedString(format: String): String {
    val calendar = NSCalendar.currentCalendar
    val components = NSDateComponents().apply {
        year = this@toFormattedString.year.toLong()
        month = this@toFormattedString.monthNumber.toLong()
        day = this@toFormattedString.dayOfMonth.toLong()
        hour = 12 // Set to noon to avoid timezone issues
    }

    val nsDate = calendar.dateFromComponents(components) ?: return ""

    val dateFormatter = NSDateFormatter().apply {
        dateFormat = format
        locale = NSLocale.localeWithLocaleIdentifier("en_US_POSIX")
    }
    return dateFormatter.stringFromDate(nsDate)
}