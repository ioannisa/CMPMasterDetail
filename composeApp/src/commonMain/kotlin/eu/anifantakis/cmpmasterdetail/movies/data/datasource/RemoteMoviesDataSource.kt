package eu.anifantakis.cmpmasterdetail.movies.data.datasource

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.core.domain.util.map
import eu.anifantakis.cmpmasterdetail.movies.data.mappers.toMovie
import eu.anifantakis.cmpmasterdetail.movies.data.networking.MoviesHttpClient
import eu.anifantakis.cmpmasterdetail.movies.data.networking.dto.MoviesDto
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.RemoteMoviesDataSource

class RemoteMoviesDataSourceImpl(
    private val httpClient: MoviesHttpClient
) : RemoteMoviesDataSource {
    override suspend fun getMovies(): DataResult<List<Movie>, DataError.Network> {
        return httpClient.get<MoviesDto>(
            route = "/3/movie/top_rated"
        ).map { moviesDto ->
            moviesDto.results.map { movieDto ->
                movieDto.toMovie()
            }
        }
    }
}