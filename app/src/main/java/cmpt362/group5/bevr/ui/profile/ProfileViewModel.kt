package cmpt362.group5.bevr.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithType
import cmpt362.group5.bevr.data.usersettings.BEVERAGE_DEFINITIONS
import cmpt362.group5.bevr.data.usersettings.DEFAULT_AVATAR_ID
import cmpt362.group5.bevr.data.usersettings.UserSettings
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = true,
    val displayName: String = "Guest",
    val avatarId: Int = DEFAULT_AVATAR_ID,
    val totalDrinks: Int = 0,
    val favouriteDrinkType: String = "-",
    val drinkTypeCounts: Map<String, Int> = emptyMap(),
)

class ProfileViewModel(
    private val userSettingsRepository: UserSettingsRepository,
    private val drinkRecordRepository: DrinkRecordRepository,
) : ViewModel() {

    companion object {
        private const val LOG_TAG = "ProfileViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                ProfileViewModel(
                    userSettingsRepository = app.container.userSettingsRepository,
                    drinkRecordRepository = app.container.drinkRecordRepository,
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                userSettingsRepository.getUserSettings(),
                drinkRecordRepository.getDrinkRecordsWithType()
            ) { settings, records ->
                buildProfileUiState(settings, records)
            }.collect { _uiState.value = it }
        }
    }

    private fun buildProfileUiState(
        settings: UserSettings,
        records: List<DrinkRecordWithType>
    ): ProfileUiState {

        val total = records.size

        // Map raw DrinkType.name → canonical ("coffee","tea","juice","liquor","boba")
        val canonicalKeys = records.map { rec ->
            drinkTypeNameToKey(rec.drinkType.name)
        }

        // Count occurrences
        val countsByKey = canonicalKeys.groupingBy { it }.eachCount()

        // Favourite drink = most common canonical group
        val favouriteKey = countsByKey.maxByOrNull { it.value }?.key
        val favouriteLabel =
            favouriteKey?.let { key ->
                BEVERAGE_DEFINITIONS.firstOrNull { it.key == key }?.label ?: key
            } ?: "-"

        // Apply active beverage filter
        val activeKeys = settings.activeBeverages
        val filteredCounts =
            if (activeKeys.isEmpty()) countsByKey
            else countsByKey.filter { it.key in activeKeys }

        Log.d(LOG_TAG,
            "Profile summary -> total=$total favourite=$favouriteLabel raw=$countsByKey filtered=$filteredCounts"
        )

        return ProfileUiState(
            isLoading = false,
            displayName = settings.displayName,
            avatarId = settings.avatarId,
            totalDrinks = total,
            favouriteDrinkType = favouriteLabel,
            drinkTypeCounts = filteredCounts,
        )
    }

    /** Canonical mapping using your new dbNames list */
    private fun drinkTypeNameToKey(name: String): String {
        val normalized = name.trim().lowercase()
        return BEVERAGE_DEFINITIONS
            .firstOrNull { it.dbNames.contains(normalized) }
            ?.key ?: normalized
    }
}
