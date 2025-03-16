package eu.anifantakis.cmpmasterdetail.movies.data

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.MoviesRepository
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.RemoteMoviesDataSource

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
) : MoviesRepository{

    override suspend fun fetchMovies(): List<Movie> {
        return when (val result = remoteDataSource.getMovies()) {
            is DataResult.Success -> result.data
            is DataResult.Failure -> emptyList()
        }
    }
}