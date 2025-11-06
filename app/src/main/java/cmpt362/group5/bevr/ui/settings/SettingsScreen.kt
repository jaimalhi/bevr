package cmpt362.group5.bevr.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The screen that allows the user to configure the application and personalize it.
 */
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Settings")
    }
}