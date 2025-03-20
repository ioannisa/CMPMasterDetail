package eu.anifantakis.cmpmasterdetail.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import eu.anifantakis.cmpmasterdetail.app.NavGraph
import eu.anifantakis.cmpmasterdetail.core.presentation.sharedKoinViewModel
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import eu.anifantakis.cmpmasterdetail.movies.presentation.MoviesListScreenRoot
import eu.anifantakis.cmpmasterdetail.movies.presentation.MoviesListViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface MoviesNavType {
    @Serializable data object MoviesList: MoviesNavType
    @Serializable data class MovieDetail(val movieId: MovieId): MoviesNavType
}

fun NavGraphBuilder.moviesGraph(navController: NavHostController, paddingValues: PaddingValues) {
    navigation<NavGraph.Movies>(
        startDestination = MoviesNavType.MoviesList,
    ) {
        composable<MoviesNavType.MoviesList> {
            val viewModel = it.sharedKoinViewModel<MoviesListViewModel>(navController)
            MoviesListScreenRoot(
                viewModel = viewModel,
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate(MoviesNavType.MovieDetail(movieId))
                }
            )
        }

        composable<MoviesNavType.MovieDetail> {
            val viewModel = it.sharedKoinViewModel<MoviesListViewModel>(navController)

            val args = it.toRoute<MoviesNavType.MovieDetail>()
            val movieId = args.movieId

            Box(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "DETAIL SCREEN \n\n ${movieId}"
                    )
                    Button(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("BACK")
                    }
                }
            }
        }
    }
}