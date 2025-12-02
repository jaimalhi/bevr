package cmpt362.group5.bevr.ui.drinklog

import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.R
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithType
import cmpt362.group5.bevr.data.drinktypes.DrinkType
import cmpt362.group5.bevr.data.images.DrinkRecordImageRepository
import coil3.compose.AsyncImage

@Composable
fun RatingStars(rating: Int) {
    Row {
        for (i in 1..5) {
            if (rating >= i) {
                // Full star
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700)  // gold
                )
            } else {
                // Hollow star
                Icon(
                    imageVector = Icons.Filled.StarBorder,
                    contentDescription = null,
                    tint = Color(0xFFFFD700)
                )
            }
        }
    }
}

@Composable
fun DrinkLogListItem(
    record: DrinkRecordWithType,
    onDelete: (DrinkRecord) -> Unit,
    onGenerateRecipe: (Long) -> Unit,
    imageRepository: DrinkRecordImageRepository
) {
    val drink = record.drinkRecord
    val type: DrinkType = record.drinkType

    val formatter = SimpleDateFormat("MMM d, yyyy 'at' h:mm a")
    val formattedTimestamp = formatter.format(drink.timestamp)

    val imageUri: Uri? = runCatching {
        imageRepository.getImageUriForDrinkRecord(drink)
    }.getOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 11.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Top row: drink info + image
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Left side info
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Drink type: ${type.name}\n" +
                                "Name: ${drink.name}\n" +
                                "$formattedTimestamp\n" +
                                "Rating: ${drink.rating}/5"
                    )

                    RatingStars(drink.rating)
                }

                // Right side image
                AsyncImage(
                    model = imageUri ?: R.drawable.beer_mug_svgrepo_com,
                    contentDescription = "Drink image",
                    modifier = Modifier
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // bottom row: buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onGenerateRecipe(drink.id) }
                ) {
                    Text("Generate recipe")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onDelete(drink) }
                ) {
                    Text("Delete")
                }
            }
        }
    }
}



enum class DrinkLogFilterMode {
    BY_RATING_ASCENDING,
    MOST_RECENT,
    BY_RATING_DESCENDING
}

/**
 * The screen that display all of the drink records the the user entered.
 */
// This Opt-in is required for Material 3's TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkLogScreen(
    onOpenRecipe: (Long) -> Unit
) {
    val app = LocalContext.current.applicationContext as BevrApplication
    val viewModel: DrinkLogViewModel = viewModel(
        factory = DrinkLogViewModel.Factory(
            app.container.drinkRecordRepository
        )
    )
    val records by viewModel.drinkRecords.collectAsState(emptyList())

    var filterMode by remember { mutableStateOf(DrinkLogFilterMode.MOST_RECENT) }

    val filteredRecords = when (filterMode) {
        DrinkLogFilterMode.BY_RATING_ASCENDING -> records.sortedBy { it.drinkRecord.rating }
        DrinkLogFilterMode.MOST_RECENT -> records.sortedByDescending { it.drinkRecord.timestamp }
        DrinkLogFilterMode.BY_RATING_DESCENDING -> records.sortedByDescending { it.drinkRecord.rating }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Drink Log",
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-11).dp),
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Sort by:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { filterMode = DrinkLogFilterMode.MOST_RECENT },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Most Recent", fontSize = 12.sp)
                    }
                    Button(
                        onClick = { filterMode = DrinkLogFilterMode.BY_RATING_DESCENDING },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Highest Rated", fontSize = 12.sp)
                    }
                    Button(
                        onClick = { filterMode = DrinkLogFilterMode.BY_RATING_ASCENDING },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Lowest Rated", fontSize = 12.sp)
                    }
                }
            }

            LazyColumn {
                items(filteredRecords) { record ->
                    DrinkLogListItem(
                        record = record,
                        onDelete = { drink ->
                            viewModel.deleteDrinkRecord(drink)
                        },
                        onGenerateRecipe = { id ->
                            onOpenRecipe(id)
                        },
                        imageRepository = app.container.drinkRecordImageRepository
                    )
                }
            }
        }
    }
}
