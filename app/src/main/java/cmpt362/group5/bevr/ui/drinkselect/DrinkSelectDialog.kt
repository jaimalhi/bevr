package cmpt362.group5.bevr.ui.drinkselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.DrawableCompat.applyTheme
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.data.usersettings.BEVERAGE_DEFINITIONS
import cmpt362.group5.bevr.data.usersettings.BeverageDefinition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The dialog that the user will use to select which drinks to primarily display in the drink log and locations.
 */
@Composable
fun DrinkSelectDialog(
    viewModel: DrinkSelectViewModel = viewModel(factory = DrinkSelectViewModel.Factory),
    onDismiss: () -> Unit = {}
) {
    val userSettings by viewModel.userSettings.observeAsState()
    val activeKeys = userSettings?.activeBeverages ?: emptySet()

    // Filter definitions to only the user's active beverages
    // Originally was displaying keys, but changed to set themes by key and UI text by label
    // So, I had to add this default definition

    // Could just make the default its own val but I was lazy
    val options = listOf(
        BeverageDefinition(key = "default", label = "Default", dbNames = emptySet())
    ) + BEVERAGE_DEFINITIONS.filter { it.key in activeKeys }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Theme", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        options.forEach { themeName ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        // Update selectedTheme in the repository
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.updateTheme(themeName.key)
                        }
                        onDismiss()
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = themeName.label,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

