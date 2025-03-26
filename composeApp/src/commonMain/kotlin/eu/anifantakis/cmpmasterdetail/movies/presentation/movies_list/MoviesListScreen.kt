package eu.anifantakis.cmpmasterdetail.movies.presentation.movies_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmpmasterdetail.composeapp.generated.resources.Res
import cmpmasterdetail.composeapp.generated.resources.compose_multiplatform
import coil3.compose.rememberAsyncImagePainter
import eu.anifantakis.cmpmasterdetail.app.global_state.domain.GlobalState
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.ObserveEffects
import eu.anifantakis.cmpmasterdetail.core.presentation.composition_locals.LocalBottomBarState
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.UIConst
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.components.AppBackground
import eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.PullToRefreshList
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import kotlinx.datetime.Clock.System
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs

@Composable
fun MoviesListScreenRoot(
    onNavigateToMovieDetails: (MovieId) -> Unit,
    viewModel: MoviesListViewModel = koinViewModel()
) {
    ObserveEffects(viewModel.events) { effect ->
        when (effect) {
            is MoviesListEffect.GotoMovieDetails -> {
                if (effect.movieId > 0)
                    onNavigateToMovieDetails(effect.movieId)
            }
            else -> { }
        }
    }

    MoviesListScreen(
        state = viewModel.state,
        globalState = viewModel.globalUiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun MoviesListScreen(
    state: MoviesListState,
    globalState: GlobalState,
    onAction: (MoviesListIntent) -> Unit,
) {
    MyAppTheme {
        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(UIConst.paddingRegular),
                verticalArrangement = Arrangement.spacedBy(UIConst.paddingSmall)
            ) {
                val bottomBarState = LocalBottomBarState.current

                MultiTapItem(
                    onClick = {
                        println("MultiTap Secret Triggered")
                        bottomBarState.toggle()
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "Movies",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        )

                        Image(
                            imageVector = AppIcons.battery, contentDescription = null,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "${globalState.batteryLevel}%",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        )
                    }
                }

                PullToRefreshList(isRefreshing = state.isRefreshing, onRefresh = {
                onAction(MoviesListIntent.RefreshMovies)
                }) {
                    LazyColumn {
                        items(
                            items = state.movies,
                            key = { it.id }
                        ) { movie ->
                            RowItem(
                                movie = movie,
                                modifier = Modifier
                                    .clickable {
                                        onAction(MoviesListIntent.SelectMovie(movie.id))
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowItem(movie: Movie, modifier: Modifier = Modifier) {
    Box (
        modifier = modifier.
            padding(vertical = 4.dp)
    ) {
        Row {
            ThumbnailLoader(imagePath = movie.posterPath)
            Column(
                modifier = Modifier
                    .align(Alignment.Top)
                    .fillMaxSize()
                    .heightIn(120.dp)
                    .padding(8.dp)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary

                )

                Text(
                    text = movie.releaseDate?.year.toString(),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun ThumbnailLoader(imagePath: String?) {
    val imagePainter: Painter

    if (!imagePath.isNullOrEmpty()) {
        val imageUrl = "https://image.tmdb.org/t/p/w200$imagePath"
        imagePainter = rememberAsyncImagePainter(imageUrl)
    }
    else {
        imagePainter = painterResource(Res.drawable.compose_multiplatform)
    }

    Image(
        painter = imagePainter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter,
        modifier = Modifier.size(120.dp)
    )
}

@Composable
fun MultiTapItem(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var prvTapTime by remember { mutableStateOf<Long>(0) }
    var tapCount by remember { mutableIntStateOf(0) }

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier // Apply modifier here (e.g., weight, padding from caller)
            .clickable(
                interactionSource = interactionSource,
                indication = null // No visual indication like ripple
            ) {
                val currentTime = System.now().toEpochMilliseconds()

                // Remove taps that are older than 1 second from the latest tap
                if (abs(currentTime - prvTapTime) < 1000) {
                    tapCount++
                    if (tapCount >= 7) {
                        tapCount = 1
                        onClick()
                    }
                } else {
                    tapCount = 1
                }
                prvTapTime = currentTime
            }
    ) {
        content()
    }
}