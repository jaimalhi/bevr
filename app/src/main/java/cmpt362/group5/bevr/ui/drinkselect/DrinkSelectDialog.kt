package cmpt362.group5.bevr.ui.drinkselect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The dialog that the user will use to select which drinks to primarily display in history and locations.
 */
@Composable
fun DrinkSelectDialog(viewModel: DrinkSelectViewModel = viewModel(factory = DrinkSelectViewModel.Factory)) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Drink Selection")
    }
}