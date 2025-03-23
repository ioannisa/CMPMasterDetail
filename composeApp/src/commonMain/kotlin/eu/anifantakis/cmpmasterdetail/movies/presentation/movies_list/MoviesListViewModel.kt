package eu.anifantakis.cmpmasterdetail.movies.presentation.movies_list

import androidx.lifecycle.viewModelScope
import eu.anifantakis.cmpmasterdetail.app.global_state.presentation.BaseGlobalViewModel
import eu.anifantakis.cmpmasterdetail.app.global_state.presentation.GlobalStateContainer
import eu.anifantakis.cmpmasterdetail.core.data.preferences.Vault
import eu.anifantakis.cmpmasterdetail.core.presentation.toComposeState
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.MoviesRepository
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

class MoviesListViewModel(
    globalStateContainer: GlobalStateContainer,
    private val repository: MoviesRepository,
    private val vault: Vault
) : BaseGlobalViewModel(globalStateContainer) {

    private var _state = MutableStateFlow(MoviesListState())
    val state by _state
        .onStart {
            onAction(MoviesListIntent.LoadMovies)

            // TESTING VAULT START
            //vault.put("test1", "ABCDEFG")
            val test1 = vault.get("test1", "DEFAULT 1")
            val test2 = vault.get("test2", "DEFAULT 2")

            println("TEST1 IS $test1")
            println("TEST2 IS $test2")
            // TESTING VAULT END
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000L),
            _state.value
        )
        .toComposeState(viewModelScope)


    private val eventChannel = Channel<MoviesListEffect>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(intent: MoviesListIntent) {
        when (intent) {
            MoviesListIntent.LoadMovies -> loadMovies()
            MoviesListIntent.RefreshMovies -> loadMovies(shouldRefresh = true)
            is MoviesListIntent.SelectMovie -> selectMovie(intent.movieId)
        }
    }

    private fun selectMovie(movieId: MovieId) {
        val movie = state.movies.firstOrNull { it.id == movieId }
        movie?.let { movie ->
            viewModelScope.launch {
                _state.update { it.copy(selectedMovie = movie) }
                eventChannel.send(MoviesListEffect.GotoMovieDetails(movieId))
            }
        }
    }

    private fun loadMovies(shouldRefresh: Boolean = false) {
        loadOfflineFirstAndObserve(shouldRefresh)
        //loadOnlyFromNetwork(shouldRefresh)
    }

    private fun loadOfflineFirstAndObserve(shouldRefresh: Boolean = false) {
        if (shouldRefresh) {
            viewModelScope.launch {
                _state.update { it.copy(isRefreshing = true) }
                repository.fetchMovies()
                _state.update { it.copy(isRefreshing = false) }
            }
        }
        else {
            viewModelScope.launch {
                withLoading {
                    collectMovies(repository.getCachedMovies()) // fetch from offline cache
                    repository.fetchMovies()                    // fetch from network and upsert to offline cache
                }
            }
        }
    }

    private fun collectMovies(moviesFlow: Flow<List<Movie>>) {
        viewModelScope.launch {
            moviesFlow.collect { movies ->
                _state.update { it.copy(movies = movies) }
            }
        }
    }

    private fun loadOnlyFromNetwork(shouldRefresh: Boolean = false) {
        if (shouldRefresh) {
            viewModelScope.launch {
                _state.update { it.copy(isRefreshing = true) }
                val movies = repository.fetchJustFromAPI()
                _state.update { it.copy(movies = movies, isRefreshing = false) }
            }
        }
        else {
            viewModelScope.launch {
                withLoading {
                    val movies = repository.fetchJustFromAPI()
                    _state.update { it.copy(movies = movies) }
                }
            }
        }
    }
}