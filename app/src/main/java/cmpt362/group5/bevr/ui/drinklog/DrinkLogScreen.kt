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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import java.util.Date

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.ui.text.intl.Locale




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
fun DrinkLogListItem(record: DrinkRecord) {
    //    It's efficient to create this once.
    val formatter = SimpleDateFormat("MMM d, yyyy 'at' h:mm a")
    val formattedTimestamp = formatter.format(record.timestamp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        //Column to get the text and stars on the same alignment
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = "Drink type: ${record.drinkTypeId}\n" +
                        "Name: ${record.name}\n" +
                        "Time: $formattedTimestamp\n" +
                        "Rating: ${record.rating}/5"
            )

            RatingStars(record.rating)
        }
    }
}

@Preview
@Composable
fun DrinkLogList() {
    val records = (1..20L).map { i -> DrinkRecord(
        i,
        drinkTypeId = 0,
        timestamp = Date(),
        name = "name",
        latitude = 0.0,
        longitude = 0.0,
        rating = (0..5).random()
    ) }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(records) {
            DrinkLogListItem(it)
        }
    }
}

/**
 * The screen that display all of the drink records the the user entered.
 */
@Composable
fun DrinkLogScreen(drinkLogViewModel: DrinkLogViewModel = viewModel(factory = DrinkLogViewModel.Factory)) {
    DrinkLogList()
}