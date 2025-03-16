package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.RoomDatabase

expect class MoviesDatabaseFactory {
    fun create(): RoomDatabase.Builder<MoviesDatabase>
}