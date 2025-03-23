package eu.anifantakis.cmpmasterdetail.movies.presentation.movies_list

import eu.anifantakis.cmpmasterdetail.core.presentation.ui.UiText
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId

sealed interface MoviesListIntent {
    data object LoadMovies : MoviesListIntent
    data object RefreshMovies : MoviesListIntent
    data class SelectMovie(val movieId: MovieId) : MoviesListIntent
}

sealed interface MoviesListEffect {
    data object MoviesListSuccess : MoviesListEffect
    data class Error(val error: UiText) : MoviesListEffect
    data class GotoMovieDetails(val movieId: MovieId) : MoviesListEffect
}

data class MoviesListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val selectedMovie: Movie? = null,
)