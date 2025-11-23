package cmpt362.group5.bevr.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.map

/**
 * Manages UI state for location screen
 */
class LocationsViewModel(private val drinkRecordsRepository: DrinkRecordRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                LocationsViewModel(
                    drinkRecordsRepository = app.container.drinkRecordRepository,
                )
            }
        }
    }
    val drinkRecords = drinkRecordsRepository.getDrinkRecordsWithType().asLiveData()
    val locationFlow = drinkRecordsRepository.getDrinkRecordsWithType()
        .map { list ->
            list.map { record -> LatLng(record.drinkRecord.latitude,
                record.drinkRecord.longitude) }
        }
        .asLiveData()
}