package cmpt362.group5.bevr.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Profile screen shown from the bottom navigation bar.
 *
 * - Header with avatar + name
 * - Stats row: total drinks, favourite drink type
 * - Bar graph of drink type counts
 * - Button to open Settings page
 */
@Composable
fun ProfileScreen(
    onOpenSettings: () -> Unit,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        val initial = uiState.displayName.firstOrNull()?.uppercase() ?: "A"
                        Text(
                            text = initial.toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Text(
                        text = uiState.displayName.ifBlank { "Guest" },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Stats row: total drinks + favourite drink type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Total Drinks",
                    value = uiState.totalDrinks.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Favourite Drink",
                    value = uiState.favouriteDrinkType,
                    modifier = Modifier.weight(1f)
                )
            }

            // Bar chart of drink types
            if (uiState.drinkTypeCounts.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Drink breakdown",
                            style = MaterialTheme.typography.titleMedium
                        )

                        BarChart(drinkTypeCounts = uiState.drinkTypeCounts)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Open settings button
            Button(
                onClick = onOpenSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Open Settings")
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun BarChart(
    drinkTypeCounts: Map<String, Int>,
    maxBarHeight: Int = 120
) {
    val entries = drinkTypeCounts.entries.sortedByDescending { it.value }
    val maxCount = entries.maxOfOrNull { it.value } ?: 0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        entries.forEach { (label, count) ->
            val heightFactor = if (maxCount > 0) count.toFloat() / maxCount else 0f
            val barHeightDp = (maxBarHeight * heightFactor).dp

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(barHeightDp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
