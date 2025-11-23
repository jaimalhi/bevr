package cmpt362.group5.bevr.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.R

/**
 * Profile screen shown from the bottom navigation bar.
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
        val minHeight = (LocalConfiguration.current.screenHeightDp * 0.8f).dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .heightIn(min = minHeight)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: avatar left, name right
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AvatarCircle(
                        avatarId = uiState.avatarId,
                        displayName = uiState.displayName,
                        size = 72.dp
                    )

                    Text(
                        text = uiState.displayName.ifBlank { "Guest" },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Stats: total drinks + favourite type
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

            // Drink breakdown
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
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

                    // Normalized chart input (sorted by BEVERAGE_DEFINITIONS)
                    DrinkBreakdownChart(
                        drinkTypeCounts = uiState.drinkTypeCounts
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // Open settings
            Button(
                onClick = onOpenSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
                Spacer(Modifier.size(8.dp))
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
        elevation = CardDefaults.cardElevation(2.dp)
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

/**
 * Shared avatar rendering used by both Profile & Settings
 */
@Composable
fun AvatarCircle(
    avatarId: Int,
    displayName: String,
    size: Dp,
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
                painterResource(R.drawable.coffee_bean_filled_roast_brew_svgrepo_com),
                contentDescription = "Coffee avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            2 -> Image(
                painterResource(R.drawable.tea_leaf_svgrepo_com),
                contentDescription = "Tea avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            3 -> Image(
                painterResource(R.drawable.orange_svgrepo_com),
                contentDescription = "Juice avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            4 -> Image(
                painterResource(R.drawable.cocktail_svgrepo_com),
                contentDescription = "Liquor avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            5 -> Image(
                painterResource(R.drawable.bubble_tea_icon),
                contentDescription = "Boba avatar",
                modifier = Modifier.size(size * 0.6f)
            )
            else -> {
                val initial = displayName.firstOrNull()?.uppercase() ?: "A"
                Text(
                    text = initial,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
