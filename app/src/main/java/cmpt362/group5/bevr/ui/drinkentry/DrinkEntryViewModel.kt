package cmpt362.group5.bevr.ui.drinkentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import kotlinx.coroutines.launch

class DrinkEntryViewModel(private val drinkRecordsRepository: DrinkRecordRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                DrinkEntryViewModel(
                    drinkRecordsRepository = app.container.drinkRecordRepository,
                )
            }
        }
    }

    fun addRecord(record: DrinkRecord) {
        viewModelScope.launch {
            drinkRecordsRepository.createDrinkRecord(record)
        }
    }
}