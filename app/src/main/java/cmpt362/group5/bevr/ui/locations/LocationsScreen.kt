package cmpt362.group5.bevr.ui.locations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The screen that shows a map of all locations where the drinks were had.
 */
@Composable
fun LocationsScreen(viewModel: LocationsViewModel = viewModel(factory = LocationsViewModel.Factory)) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Locations")
    }
}