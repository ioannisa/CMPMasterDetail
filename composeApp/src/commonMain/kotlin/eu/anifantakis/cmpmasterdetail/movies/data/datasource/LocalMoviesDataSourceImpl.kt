package eu.anifantakis.cmpmasterdetail.movies.data.datasource

import androidx.sqlite.SQLiteException
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataError
import eu.anifantakis.cmpmasterdetail.core.domain.util.DataResult
import eu.anifantakis.cmpmasterdetail.movies.data.database.MoviesDao
import eu.anifantakis.cmpmasterdetail.movies.data.mappers.toEntity
import eu.anifantakis.cmpmasterdetail.movies.data.mappers.toMovie
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.LocalMoviesDataSource
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocalMoviesDataSourceImpl(
    private val moviesDao: MoviesDao
): LocalMoviesDataSource {
    override fun getMovies(): Flow<List<Movie>> {
        // Use the flow builder to create a Flow from the suspend function
        return flow {
            val movies = moviesDao.getMovies().map { it.toMovie() }
            emit(movies)
        }
    }

    override suspend fun upsertMovie(movie: Movie): DataResult<MovieId, DataError.Local> {
        return try {
            val entity = movie.toEntity()
            moviesDao.upsertMovie(entity)
            DataResult.Success(entity.id)
        } catch (e: SQLiteException) {
            DataResult.Failure(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertMovies(movies: List<Movie>): DataResult<List<MovieId>, DataError.Local> {
        return try {
            val entities = movies.map { it.toEntity() }
            moviesDao.upsertMovies(entities)
            DataResult.Success(entities.map { it.id })
        } catch (e: SQLiteException) {
            DataResult.Failure(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteMovie(id: Int) {
        moviesDao.deleteMovie(id)
    }

    override suspend fun deleteAllMovies() {
        moviesDao.deleteAllMovies()
    }
}