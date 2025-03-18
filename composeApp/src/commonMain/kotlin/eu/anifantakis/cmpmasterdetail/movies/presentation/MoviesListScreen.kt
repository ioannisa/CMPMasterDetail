package eu.anifantakis.cmpmasterdetail.movies.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cmpmasterdetail.composeapp.generated.resources.Poppins_Bold
import cmpmasterdetail.composeapp.generated.resources.Poppins_Italic
import cmpmasterdetail.composeapp.generated.resources.Poppins_Regular
import cmpmasterdetail.composeapp.generated.resources.Res
import cmpmasterdetail.composeapp.generated.resources.compose_multiplatform
import coil3.compose.rememberAsyncImagePainter
import eu.anifantakis.cmpmasterdetail.core.presentation.MyAppTheme
import eu.anifantakis.cmpmasterdetail.core.presentation.ObserveEffects
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.UIConst
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.components.AppBackground
import eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.PullToRefreshList
import eu.anifantakis.cmpmasterdetail.movies.domain.Movie
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.MovieId
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

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
        onAction = viewModel::onAction
    )
}






@Composable
private fun MoviesListScreen(
    state: MoviesListState,
    onAction: (MoviesListIntent) -> Unit,
) {
    MyAppTheme {
        AppBackground {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(UIConst.padding),
                verticalArrangement = Arrangement.spacedBy(UIConst.paddingSmall)
            ) {
                var isRefeshing by remember { mutableStateOf(false) }


                val font = Font(
                    resource = Res.font.Poppins_Bold,
                    weight = FontWeight.Bold,
                    style = FontStyle.Normal
                )
                val font2 = Font(
                    resource = Res.font.Poppins_Regular,
                    weight = FontWeight.Normal,
                    style = FontStyle.Normal
                )
                val font3 = Font(
                    resource = Res.font.Poppins_Italic,
                    weight = FontWeight.Normal,
                    style = FontStyle.Italic
                )

                val famiy = FontFamily(font, font2, font3)


                Text(
                    text = "ABCDEFG hijklmn opqrst uvw",
                    fontFamily = famiy
                )




                PullToRefreshList(isRefreshing = isRefeshing, onRefresh = {
                    isRefeshing = true
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
    Card(
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

                )

                Text(text = movie.releaseDate?.year.toString())
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