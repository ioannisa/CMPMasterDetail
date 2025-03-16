package eu.anifantakis.cmpmasterdetail.movies.data.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class LocalDateTypeConverter {
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? {
        return localDate?.toString()  // ISO-8601 format (yyyy-MM-dd)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it) }
    }
}