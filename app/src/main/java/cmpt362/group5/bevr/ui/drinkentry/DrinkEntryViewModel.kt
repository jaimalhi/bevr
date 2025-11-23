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
import cmpt362.group5.bevr.data.drinktypes.DrinkTypeRepository
import cmpt362.group5.bevr.data.images.DrinkRecordImageRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

/**
 * Manages UI state for drink entry screen
 */
class DrinkEntryViewModel(
    private val drinkRecordsRepository: DrinkRecordRepository,
    private val drinkTypeRepository: DrinkTypeRepository,
    private val drinkRecordImageRepository: DrinkRecordImageRepository,
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                DrinkEntryViewModel(
                    drinkRecordsRepository = app.container.drinkRecordRepository,
                    drinkTypeRepository = app.container.drinkTypeRepository,
                    drinkRecordImageRepository = app.container.drinkRecordImageRepository,
                )
            }
        }
    }

    val drinkTypes = drinkTypeRepository.getDrinkTypes()

    fun addRecord(
        drinkTypeId: Long,
        drinkName: String,
        drinkRating: Int,
        location: LatLng,
        drinkImageFile: File
    ) {
        viewModelScope.launch {
            val id = drinkRecordsRepository.createDrinkRecord(
                DrinkRecord(
                    name = drinkName,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    drinkTypeId = drinkTypeId,
                    rating = drinkRating,
                )
            )
            val newDrinkRecord = drinkRecordsRepository.getDrinkRecord(id).first()
            drinkRecordImageRepository.saveImageForDrinkRecord(newDrinkRecord, drinkImageFile)
        }
    }
}