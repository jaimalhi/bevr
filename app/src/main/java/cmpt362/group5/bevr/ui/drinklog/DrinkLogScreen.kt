package cmpt362.group5.bevr.ui.drinklog

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

@Composable
fun DrinkLogListItem(record: DrinkRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
    ) {
        Text("Record ${record.id}",Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun DrinkLogList() {
    val records = (1..20).map { i -> DrinkRecord(i) }
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