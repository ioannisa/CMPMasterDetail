package eu.anifantakis.cmpmasterdetail.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Upsert
    suspend fun upsertMovie(movie: MovieEntity)

    @Upsert
    suspend fun upsertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity ORDER BY voteAverage DESC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAllMovies()

    @Delete
    suspend fun deleteMovies(movies: List<MovieEntity>)
}