package eu.anifantakis.cmpmasterdetail.movies.data.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class LocalDateTimeZoneUTCToLongTypeConverter {
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): Long? {
        return localDate?.let {
            val startOfDay = LocalDateTime(localDate, LocalTime(0, 0))
            startOfDay.toInstant(TimeZone.UTC).toEpochMilliseconds()
        }
    }

    @TypeConverter
    fun toLocalDate(long: Long?): LocalDate? {
        return long?.let {
            Instant.fromEpochMilliseconds(long)
                .toLocalDateTime(TimeZone.UTC)
                .date
        }
    }
}