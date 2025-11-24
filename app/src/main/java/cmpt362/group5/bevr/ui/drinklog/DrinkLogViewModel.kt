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
import cmpt362.group5.bevr.data.images.DrinkRecordImageRepository
import java.io.File

class DrinkLogViewModel(
    private val repository: DrinkRecordRepository,
    private val imageRepo: DrinkRecordImageRepository
) : ViewModel() {

    fun getImageUriSafe(record: DrinkRecord): Uri? {
        val uri = imageRepo.getImageUriForDrinkRecord(record)

        // If null or not a file URI, reject
        val path = uri?.path ?: return null

        val file = File(path)
        return if (file.exists() && file.canRead()) uri else null
    }

    // Expose the Flow as a StateFlow for Compose
    val drinkRecords: StateFlow<List<DrinkRecordWithType>> =
        repository.getDrinkRecordsWithType()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addDrinkRecord(record: DrinkRecord) {
        viewModelScope.launch {
            repository.createDrinkRecord(record)
        }
    }

    fun deleteDrinkRecord(record: DrinkRecord) {
        viewModelScope.launch {
            repository.deleteDrinkRecord(record)
        }
    }

    // Factory for Compose integration
    class Factory(
        private val recordRepo: DrinkRecordRepository,
        private val imageRepo: DrinkRecordImageRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DrinkLogViewModel(recordRepo, imageRepo) as T
        }
    }
}
