package cmpt362.group5.bevr.ui.settings

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch

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

    companion object {
        private const val LOG_TAG = "SettingsViewModel"

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

    /**
     * Flag used by the UI to know when a save has completed.
     * SettingsScreen observes this and navigates back when it flips to true.
     */
    var saveCompleted by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            // Take a single snapshot of current settings when the screen starts.
            val settings = userSettingsRepository.getUserSettings().first()
            Log.d(LOG_TAG, "Initial settings loaded: $settings")
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
     * The SettingsScreen listens to [saveCompleted] to navigate back when done.
     */
    fun onSave() {
        val current = _uiState.value

        // This is the actual set that should ALWAYS be persisted
        val active = current.activeBeverages.ifEmpty { DEFAULT_ACTIVE_BEVERAGES }

        val newSettings = UserSettings(
            displayName = current.displayName,
            avatarId = current.avatarId,
            activeBeverages = active,
        )

        saveCompleted = false

        viewModelScope.launch {
            userSettingsRepository.updateUserSettings(newSettings)

            // 1. Sync initialSettings with real persisted value
            initialSettings = newSettings

            // 2. Sync UI state with real persisted value
            _uiState.update {
                it.copy(
                    displayName = newSettings.displayName,
                    avatarId = newSettings.avatarId,
                    activeBeverages = newSettings.activeBeverages,
                    isDirty = false
                )
            }

            // 3. Notify the UI that we're done
            saveCompleted = true
        }
    }


    /**
     * Called by the UI once it has reacted to save completion (e.g. navigated back).
     * This avoids stale true values if the ViewModel is reused.
     */
    fun clearSaveCompletedFlag() {
        saveCompleted = false
    }
}
