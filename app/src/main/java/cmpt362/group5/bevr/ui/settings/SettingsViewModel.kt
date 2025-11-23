package cmpt362.group5.bevr.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.usersettings.DEFAULT_AVATAR_ID
import cmpt362.group5.bevr.data.usersettings.DEFAULT_ACTIVE_BEVERAGES
import cmpt362.group5.bevr.data.usersettings.UserSettings
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log

/**
 * Manages data and operations for the settings screen.
 * Keeps an editable copy of the settings and only persists when the user taps "Save".
 */
class SettingsViewModel(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    data class SettingsUiState(
        val displayName: String = "",
        val avatarId: Int = DEFAULT_AVATAR_ID,
        val activeBeverages: Set<String> = emptySet(),
        val isDirty: Boolean = false,
        val isLoading: Boolean = true,
    )

    data class BeverageOption(val key: String, val label: String)

    companion object {
        private const val TAG = "SettingsViewModel"

        // Beverage options used by the checklist in settings
        val BEVERAGE_OPTIONS = listOf(
            BeverageOption("coffee", "Coffee"),
            BeverageOption("tea", "Tea"),
            BeverageOption("juice", "Juice"),
            BeverageOption("liquor", "Liquor"),
            BeverageOption("boba", "Boba / Bubble Tea"),
        )

        // Number of avatar slots to choose from (0..5)
        const val AVATAR_COUNT: Int = 6

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                SettingsViewModel(
                    userSettingsRepository = app.container.userSettingsRepository,
                )
            }
        }
    }

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    // Snapshot of what is currently persisted in DataStore.
    private var initialSettings: UserSettings? = null

    init {
        viewModelScope.launch {
            // Take a single snapshot of current settings when the screen starts.
            val settings = userSettingsRepository.getUserSettings().first()
            Log.d(TAG, "Initial settings loaded: $settings")
            initialSettings = settings
            _uiState.value = SettingsUiState(
                displayName = settings.displayName,
                avatarId = settings.avatarId,
                activeBeverages = settings.activeBeverages,
                isDirty = false,
                isLoading = false,
            )
        }
    }

    private fun computeIsDirty(newState: SettingsUiState): Boolean {
        val initial = initialSettings ?: return false
        return initial.displayName != newState.displayName ||
                initial.avatarId != newState.avatarId ||
                initial.activeBeverages != newState.activeBeverages
    }

    fun onDisplayNameChange(newName: String) {
        _uiState.update { current ->
            val updated = current.copy(displayName = newName)
            updated.copy(isDirty = computeIsDirty(updated))
        }
    }

    fun onAvatarSelected(newAvatarId: Int) {
        _uiState.update { current ->
            val updated = current.copy(avatarId = newAvatarId)
            updated.copy(isDirty = computeIsDirty(updated))
        }
    }

    fun onBeverageToggled(key: String) {
        _uiState.update { current ->
            val newSet = current.activeBeverages.toMutableSet().apply {
                if (contains(key)) remove(key) else add(key)
            }
            val updated = current.copy(activeBeverages = newSet)
            updated.copy(isDirty = computeIsDirty(updated))
        }
    }

    fun setAllBeverages(selected: Boolean) {
        _uiState.update { current ->
            val newSet = if (selected) {
                DEFAULT_ACTIVE_BEVERAGES
            } else {
                emptySet()
            }
            val updated = current.copy(activeBeverages = newSet)
            updated.copy(isDirty = computeIsDirty(updated))
        }
    }

    /**
     * Persists current editable settings to the repository and resets dirty state.
     * Caller (SettingsScreen) decides how/when to navigate away.
     */
    fun onSave() {
        val current = _uiState.value
        val newSettings = UserSettings(
            displayName = current.displayName,
            avatarId = current.avatarId,
            activeBeverages = current.activeBeverages,
        )

        Log.d(TAG, "Saving settings: $newSettings")

        viewModelScope.launch {
            userSettingsRepository.updateUserSettings(newSettings)
            initialSettings = newSettings
            _uiState.update { it.copy(isDirty = false) }
        }
    }
}
