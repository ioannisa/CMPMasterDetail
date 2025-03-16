package eu.anifantakis.cmpmasterdetail.movies.data

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.core.domain.util.EmptyDataResult
import eu.anifantakis.cmpmasterdetail.core.domain.util.asEmptyDataResult
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.MoviesRepository
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.LocalMoviesDataSource
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.RemoteMoviesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource
) : MoviesRepository{

    // Fetch from Ktor -> Upsert on ROOM
    override suspend fun fetchMovies(): EmptyDataResult<DataError> {
         return when (val result = remoteDataSource.getMovies()) {
             is DataResult.Success -> { localDataSource.upsertMovies(result.data).asEmptyDataResult() }
             is DataResult.Failure -> { result.asEmptyDataResult() }
         }
    }

    override suspend fun fetchJustFromAPI(): List<Movie> {
        return when (val result = remoteDataSource.getMovies()) {
            is DataResult.Success -> { result.data }
            is DataResult.Failure -> { emptyList() }
        }
    }

    // Fetch from ROOM
    override fun getMovies(): Flow<List<Movie>> {
        return localDataSource.getMovies()
    }

    // Upsert Movie to ROOM
    override suspend fun upsertMovie(movie: Movie): EmptyDataResult<DataError> {
        val localResult = localDataSource.upsertMovie(movie)

        if (localResult !is DataResult.Success) {
            return localResult.asEmptyDataResult()
        }

        return localResult.asEmptyDataResult()
    }

    // Delete Movie from ROOM
    override suspend fun deleteMovie(id: MovieId) {
        localDataSource.deleteMovie(id)
    }
}