package eu.anifantakis.cmpmasterdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import cmpmasterdetail.composeapp.generated.resources.Res
import cmpmasterdetail.composeapp.generated.resources.compose_multiplatform
import eu.anifantakis.cmpmasterdetail.core.data.Log

import eu.anifantakis.cmpmasterdetail.movies.presentation.MoviesListScreenRoot

@Composable
@Preview
fun App() {
    MaterialTheme {
        MoviesListScreenRoot()

        Box(
            modifier = Modifier.safeDrawingPadding()
        ) {
            MoviesListScreenRoot()
        }
    }
}