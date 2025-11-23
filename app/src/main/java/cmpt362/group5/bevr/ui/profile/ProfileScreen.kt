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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import cmpt362.group5.bevr.ui.settings.SettingsViewModel

/**
 * Profile screen shown from the bottom navigation bar.
 *
 * - Header with avatar + name
 * - Stats row: total drinks, favourite drink type
 * - Bar graph of drink type counts (always visible, with skeleton when empty)
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
        val configuration = LocalConfiguration.current
        val minHeight = configuration.screenHeightDp.dp * 0.8f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .heightIn(min = minHeight)
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
                    AvatarCircle(
                        avatarId = uiState.avatarId,
                        displayName = uiState.displayName,
                        size = 80.dp
                    )

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

            // Bar chart of drink types — always visible
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
    val entries = if (drinkTypeCounts.isNotEmpty()) {
        drinkTypeCounts.entries.sortedByDescending { it.value }
    } else {
        // Skeleton entries when there is no data
        listOf(
            "Coffee" to 1,
            "Tea" to 1,
            "Juice" to 1,
            "Liquor" to 1,
            "Boba" to 1,
        )
    }

    val maxCount = entries.maxOfOrNull { it.second } ?: 1

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (drinkTypeCounts.isEmpty()) {
            Text(
                text = "No drinks logged yet. Add some to see your breakdown!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            entries.forEach { (label, count) ->
                val heightFactor = count.toFloat() / maxCount
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
}

/**
 * Shared avatar rendering used by both Profile and Settings.
 *
 * avatarId:
 * 0 = default (first letter)
 * 1 = coffee icon
 * 2 = tea icon
 * 3 = juice icon
 * 4 = liquor icon
 * 5 = boba icon
 */
@Composable
fun AvatarCircle(
    avatarId: Int,
    displayName: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        when (avatarId) {
            1 -> Image(
                painter = painterResource(R.drawable.coffee_bean_filled_roast_brew_svgrepo_com),
                contentDescription = "Coffee avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            2 -> Image(
                painter = painterResource(R.drawable.tea_leaf_svgrepo_com),
                contentDescription = "Tea avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            3 -> Image(
                painter = painterResource(R.drawable.orange_svgrepo_com),
                contentDescription = "Juice avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            4 -> Image(
                painter = painterResource(R.drawable.cocktail_svgrepo_com),
                contentDescription = "Liquor avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            5 -> Image(
                painter = painterResource(R.drawable.bubble_tea_icon),
                contentDescription = "Boba avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            else -> {
                val initial = displayName.firstOrNull()?.uppercase() ?: "A"
                Text(
                    text = initial.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
