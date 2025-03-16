package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object MoviesDatabaseConstructor: RoomDatabaseConstructor<MoviesDatabase> {
    override fun initialize(): MoviesDatabase
}