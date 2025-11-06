package cmpt362.group5.bevr.ui.drinklog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import kotlinx.coroutines.launch

/**
 * Manages UI state for drink logs screen
 */
class DrinkLogViewModel(private val drinkRecordsRepository: DrinkRecordRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                DrinkLogViewModel(
                    drinkRecordsRepository = app.container.drinkRecordRepository,
                )
            }
        }
    }

    val drinkRecords = drinkRecordsRepository.getDrinkRecords().asLiveData()

    fun deleteRecord(record: DrinkRecord) {
        viewModelScope.launch {
            drinkRecordsRepository.deleteDrinkRecord(record)
        }
    }
}