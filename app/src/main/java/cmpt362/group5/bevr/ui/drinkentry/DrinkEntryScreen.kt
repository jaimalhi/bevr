package cmpt362.group5.bevr.ui.drinkentry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The screen that allows the user to enter details about the drink they just had.
 */
@Composable
fun DrinkEntryScreen(viewModel: DrinkEntryViewModel = viewModel(factory = DrinkEntryViewModel.Factory)) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Add Drink Record")
    }
}