package eu.anifantakis.cmpmasterdetail.movies.domain.datasource

import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie

interface RemoteMoviesDataSource {
    suspend fun getMovies(): DataResult<List<Movie>, DataError.Network>
}