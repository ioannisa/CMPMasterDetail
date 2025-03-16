package eu.anifantakis.cmpmasterdetail.movies.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class MoviesDatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<MoviesDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(MoviesDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}