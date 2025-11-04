package cmpt362.group5.bevr.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cmpt362.group5.bevr.BevrApplication
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository

/**
 * Manages data and data operations as well as provide lifecycle their operations for the settings screen.
 */
class SettingsViewModel(private val userSettingsRepository: UserSettingsRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as BevrApplication)
                SettingsViewModel(
                    userSettingsRepository = app.container.userSettingsRepository,
                )
            }
        }
    }

    /**
     * The settings screen will observe this live data to update UI.
     */
    val userSettings = userSettingsRepository.getUserSettings().asLiveData()
}