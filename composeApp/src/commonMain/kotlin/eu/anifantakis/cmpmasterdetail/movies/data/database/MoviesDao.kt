package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Upsert
    suspend fun upsertMovie(movie: MovieEntity): Long

    @Upsert
    suspend fun upsertMovies(movies: List<MovieEntity>): List<Long>

    // For iOS, we can't use Flow directly
    @Query("SELECT * FROM MovieEntity ORDER BY voteAverage DESC")
    suspend fun getMovies(): List<MovieEntity>

    // For Android, we can keep Flow in a separate function
    @Query("SELECT * FROM MovieEntity ORDER BY voteAverage DESC")
    fun getMoviesFlow(): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    suspend fun deleteMovie(id: Int): Int

    @Delete
    suspend fun deleteMovie(movie: MovieEntity): Int

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovies(): Int

    @Delete
    suspend fun deleteMovies(movies: List<MovieEntity>): Int
}