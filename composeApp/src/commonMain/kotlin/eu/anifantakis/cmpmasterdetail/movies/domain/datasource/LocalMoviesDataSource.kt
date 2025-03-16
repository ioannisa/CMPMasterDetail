package eu.anifantakis.cmpmasterdetail.movies.domain.datasource

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import kotlinx.coroutines.flow.Flow

typealias MovieId = Int

interface LocalMoviesDataSource {
    fun getMovies(): Flow<List<Movie>>
    suspend fun upsertMovie(movie: Movie): DataResult<MovieId, DataError.Local>
    suspend fun upsertMovies(movies: List<Movie>): DataResult<List<MovieId>, DataError.Local>
    suspend fun deleteMovie(id: Int)
    suspend fun deleteAllMovies()
}