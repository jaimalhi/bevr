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
import cmpt362.group5.bevr.data.usersettings.DEFAULT_AVATAR_ID
import cmpt362.group5.bevr.data.usersettings.UserSettings
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = true,
    val displayName: String = "Guest",
    val avatarId: Int = DEFAULT_AVATAR_ID,
    val totalDrinks: Int = 0,
    val favouriteDrinkType: String = "-",
    val drinkTypeCounts: Map<String, Int> = emptyMap(),
)

/**
 * ViewModel for the Profile screen.
 * Combines user settings with drink record stats.
 */
class ProfileViewModel(
    private val userSettingsRepository: UserSettingsRepository,
    private val drinkRecordRepository: DrinkRecordRepository,
) : ViewModel() {

    companion object {
        const val LOG_TAG = "ProfileViewModel"

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
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    private fun buildProfileUiState(
        settings: UserSettings,
        records: List<DrinkRecordWithType>
    ): ProfileUiState {
        val totalDrinks = records.size

        // Favourite drink type, using display name from DrinkType.name
        val favouriteDrinkType = records
            .groupBy { it.drinkType.name }
            .maxByOrNull { (_, recs) -> recs.size }
            ?.key ?: "-"

        // Build counts by "key" (coffee, tea, juice, liquor, boba)
        val countsByKey: Map<String, Int> = records
            .groupingBy { drinkTypeNameToKey(it.drinkType.name) }
            .eachCount()

        // Filter by active beverages (if none selected, show all)
        val activeKeys = settings.activeBeverages
        val filteredCounts: Map<String, Int> =
            if (activeKeys.isEmpty()) {
                countsByKey
            } else {
                countsByKey.filter { (key, _) -> key in activeKeys }
            }

        // Logging for debugging
        Log.d(
            LOG_TAG,
            "Profile state build: total=$totalDrinks, favourite=$favouriteDrinkType, " +
                    "activeKeys=$activeKeys, countsByKey=$countsByKey, filtered=$filteredCounts"
        )

        return ProfileUiState(
            isLoading = false,
            displayName = settings.displayName,
            avatarId = settings.avatarId,
            totalDrinks = totalDrinks,
            favouriteDrinkType = favouriteDrinkType,
            drinkTypeCounts = filteredCounts,
        )
    }

    /**
     * Normalize a DrinkType.name (e.g. "Coffee") into our settings key (e.g. "coffee").
     * Adjust the mapping here if your DB names differ.
     */
    private fun drinkTypeNameToKey(name: String): String =
        when (name.trim().lowercase()) {
            "coffee" -> "coffee"
            "tea" -> "tea"
            "juice" -> "juice"
            "boba", "bubble tea" -> "boba"
            "liquor", "alcohol", "beer", "wine", "vodka" -> "liquor"
            else -> name.trim().lowercase()
        }
}
