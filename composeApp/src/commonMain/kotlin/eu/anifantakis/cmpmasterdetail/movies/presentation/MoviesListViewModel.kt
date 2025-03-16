package eu.anifantakis.cmpmasterdetail.movies.presentation

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.cmpmasterdetail.core.presentation.toComposeState
import eu.anifantakis.cmpmasterdetail.core.presentation.ui.UiText
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.MoviesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface MoviesListIntent {
    data object LoadMovies : MoviesListIntent
    data class SelectMovie(val movieId: Int) : MoviesListIntent
}

sealed interface MoviesListEffect {
    data object MoviesListSuccess : MoviesListEffect
    data class Error(val error: UiText) : MoviesListEffect
    data class GotoMovieDetails(val movieId: Int) : MoviesListEffect
}

data class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val selectedMovie: Movie? = null,
)

class MoviesListViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    private var _state = MutableStateFlow(MoviesListState())
    val state by _state
        .onStart {
            loadMovies()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )
        .toComposeState(viewModelScope)


    private val eventChannel = Channel<MoviesListEffect>()
    val events = eventChannel.receiveAsFlow()

    private fun loadMovies() {
        loadOfflineFirstAndObserve()
        //loadOnlyFromNetwork()
    }

    private fun loadOfflineFirstAndObserve() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getMovies()
                .collect { movies ->
                    _state.update { it.copy(movies = movies) }
                }
        }

        viewModelScope.launch {
            repository.fetchMovies()
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun loadOnlyFromNetwork() {
        viewModelScope.launch {
            val movies = repository.fetchJustFromAPI()
            _state.update { it.copy(movies = movies, isLoading = false) }
        }
    }
}