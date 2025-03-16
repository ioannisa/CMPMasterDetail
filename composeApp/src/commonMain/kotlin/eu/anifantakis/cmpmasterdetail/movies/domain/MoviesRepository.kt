package eu.anifantakis.cmpmasterdetail.movies.domain

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.EmptyDataResult
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    // KTOR
    suspend fun fetchMovies(): EmptyDataResult<DataError>
    suspend fun fetchJustFromAPI(): List<Movie>

    // ROOM
    fun getMovies(): Flow<List<Movie>>
    suspend fun upsertMovie(movie: Movie): EmptyDataResult<DataError>
    suspend fun deleteMovie(id: MovieId)
}