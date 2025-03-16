package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.anifantakis.cmpmasterdetail.movies.data.database.converters.LocalDateTypeConverter
import eu.anifantakis.cmpmasterdetail.movies.data.database.converters.StringListTypeConverter

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class,
    LocalDateTypeConverter::class
)
@ConstructedBy(MoviesDatabaseConstructor::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract val moviesDao: MoviesDao

    companion object {
        const val DB_NAME = "movies.db"
    }
}