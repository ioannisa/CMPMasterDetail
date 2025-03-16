package eu.anifantakis.cmpmasterdetail.movies.domain

import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun fetchMovies(): List<Movie>
}