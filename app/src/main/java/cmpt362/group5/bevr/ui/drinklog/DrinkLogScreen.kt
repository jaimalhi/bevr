package cmpt362.group5.bevr.ui.drinklog

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import DrinkLogViewModel
import android.net.Uri
import cmpt362.group5.bevr.BevrApplication

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cmpt362.group5.bevr.R

import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithType
import coil3.compose.AsyncImage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cmpt362.group5.bevr.data.images.DrinkRecordImageRepository


@Composable
fun RatingStars(rating: Int) {
    Row {
        for (i in 1..5) {

            if (rating >= i) {
                // Full star
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFD700)  //gold
                )
            }

            else {
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
fun DrinkLogListItem(record: DrinkRecordWithType, onDelete: (DrinkRecord) -> Unit, imageRepository: DrinkRecordImageRepository) {
    val drink = record.drinkRecord       // The raw DrinkRecord
    val type = record.drinkType         // The related DrinkType

    val formatter = SimpleDateFormat("MMM d, yyyy 'at' h:mm a")
    val formattedTimestamp = formatter.format(drink.timestamp)

    // Get the image URI, if exists
    val imageUri: Uri? = runCatching {
        imageRepository.getImageUriForDrinkRecord(drink)
    }.getOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 11.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Left column for text and stars
            Column(
                modifier = Modifier
                    .weight(1f) // Take all remaining horizontal space
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "Drink type: ${type.name}\n" +
                            "Name: ${drink.name}\n" +
                            "$formattedTimestamp\n" +
                            "Rating: ${drink.rating}/5"
                )

                RatingStars(drink.rating)

                Row(modifier = Modifier.padding(top = 8.dp)) {
                    androidx.compose.material3.Button(
                        onClick = { onDelete(drink) }
                    ) {
                        Text("Delete")
                    }
                }
            }

            // Right side image
            AsyncImage(
                model = imageUri ?: R.drawable.beer_mug_svgrepo_com,
                contentDescription = "Drink image",
                modifier = Modifier
                    .height(140.dp)
                    .clip(RectangleShape)
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun DrinkLogList() {
//
//    // Fake DrinkType for preview
//    val fakeDrinkType = DrinkType(
//        id = 0,
//        name = "Coffee",
//        icon = DrinkTypeIcon.COFFEE
//    )
//
//    // Fake records wrapped into DrinkRecordWithType
//    val records = (1L..20L).map { i ->
//        DrinkRecordWithType(
//            drinkRecord = DrinkRecord(
//                id = i,
//                drinkTypeId = 0,
//                timestamp = Date(),
//                name = "Sample Drink #$i",
//                latitude = 0.0,
//                longitude = 0.0,
//                rating = (0..5).random()
//            ),
//            drinkType = fakeDrinkType
//        )
//    }
//
//    LazyColumn(modifier = Modifier.fillMaxWidth()) {
//        items(records) { record ->
////            DrinkLogListItem(record, )
//        }
//    }
//}


/**
 * The screen that display all of the drink records the the user entered.
 */
// This Opt-in is required for Material 3's TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkLogScreen() {
    val app = LocalContext.current.applicationContext as BevrApplication
    val viewModel: DrinkLogViewModel = viewModel(
        factory = DrinkLogViewModel.Factory(
            app.container.drinkRecordRepository,
            app.container.drinkRecordImageRepository
        )
    )
    val records by viewModel.drinkRecords.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Your Drink Log",
                        modifier = Modifier.fillMaxWidth().offset(y = (-11).dp),
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(records) { record ->
                DrinkLogListItem(
                    record,
                    onDelete = { drink ->
                        viewModel.deleteDrinkRecord(drink)
                    },
                    imageRepository = app.container.drinkRecordImageRepository
                )
            }
        }
    }
}

