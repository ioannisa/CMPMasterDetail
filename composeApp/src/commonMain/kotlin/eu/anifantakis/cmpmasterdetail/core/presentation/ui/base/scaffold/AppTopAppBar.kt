package eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import eu.anifantakis.cmpmasterdetail.core.presentation.designsystem.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(title: String? = null, onBackPress: (() -> Unit)? = null) {

    TopAppBar(
        title = { Text(title ?: "", color = MaterialTheme.colorScheme.onPrimaryContainer) },
        actions = {} ,

        navigationIcon = {
            onBackPress?.let {
                IconButton(onClick = it) {
                    Icon(AppIcons.backArrow, contentDescription = "Back")
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}