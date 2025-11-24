package cmpt362.group5.bevr.ui.drinkselect

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.usersettings.DEFAULT_ACTIVE_BEVERAGES
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Manages UI state for drink select dialog
 */
class DrinkSelectViewModel(private val userSettingsRepository: UserSettingsRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                DrinkSelectViewModel(
                    userSettingsRepository = app.container.userSettingsRepository,
                )
            }
        }
    }

    /**
     * The settings screen will observe this live data to update UI.
     */
    val userSettings = userSettingsRepository.getUserSettings().asLiveData()

    fun updateTheme(theme: String) {
        viewModelScope.launch {
            val currentSettings = userSettingsRepository.getUserSettings().first()

            userSettingsRepository.updateUserSettings(
                currentSettings.copy(selectedTheme = theme)
            )
        }
    }
}