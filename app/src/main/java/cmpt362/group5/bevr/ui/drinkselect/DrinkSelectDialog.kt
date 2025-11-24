package cmpt362.group5.bevr.ui.drinkselect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.DrawableCompat.applyTheme
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.data.usersettings.BEVERAGE_DEFINITIONS
import cmpt362.group5.bevr.data.usersettings.BeverageDefinition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import cmpt362.group5.bevr.R

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
        Text(
            "Select Theme",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        options.forEach { theme ->
            ThemeOptionRow(
                themeKey = theme.key,
                label = theme.label,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.updateTheme(theme.key)
                    }
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun ThemeOptionRow(
    themeKey: String,
    label: String,
    onClick: () -> Unit
) {
    // Map theme keys to the same icons used in AvatarCircle
    val iconRes = when (themeKey) {
        "default" -> R.drawable.ic_launcher_foreground
        "coffee" -> R.drawable.coffee_bean_filled_roast_brew_svgrepo_com
        "tea" -> R.drawable.tea_leaf_svgrepo_com
        "juice" -> R.drawable.orange_svgrepo_com
        "liquor" -> R.drawable.cocktail_svgrepo_com
        "boba" -> R.drawable.bubble_tea_icon
        else -> null  // default theme has no icon
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = label,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


