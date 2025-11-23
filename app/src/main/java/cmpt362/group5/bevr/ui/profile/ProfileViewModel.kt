package cmpt362.group5.bevr.ui.profile

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

        val countsByType: Map<String, Int> = records
            .groupingBy { it.drinkType.name }
            .eachCount()

        val favouriteDrinkType = countsByType
            .maxByOrNull { it.value }
            ?.key ?: "-"

        return ProfileUiState(
            isLoading = false,
            displayName = settings.displayName,
            avatarId = settings.avatarId,
            totalDrinks = totalDrinks,
            favouriteDrinkType = favouriteDrinkType,
            drinkTypeCounts = countsByType,
        )
    }
}
