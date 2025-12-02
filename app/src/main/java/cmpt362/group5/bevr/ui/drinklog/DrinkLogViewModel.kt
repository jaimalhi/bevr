package cmpt362.group5.bevr.ui.drinklog

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithType

class DrinkLogViewModel(
    private val repository: DrinkRecordRepository,
) : ViewModel() {

    // Expose the Flow as a StateFlow for Compose
    val drinkRecords: StateFlow<List<DrinkRecordWithType>> =
        repository.getDrinkRecordsWithType()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun deleteDrinkRecord(record: DrinkRecord) {
        viewModelScope.launch {
            repository.deleteDrinkRecord(record)
        }
    }

    // Factory for Compose integration
    class Factory(
        private val recordRepo: DrinkRecordRepository,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DrinkLogViewModel(recordRepo) as T
        }
    }
}
