@file:OptIn(ExperimentalForeignApi::class)

package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask

actual class MoviesDatabaseFactory(){
    actual fun create(): RoomDatabase.Builder<MoviesDatabase> {
        val dbFile = documentDirectory() + "/${MoviesDatabase.DB_NAME}"
        return Room.databaseBuilder<MoviesDatabase>(
            name = dbFile
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}