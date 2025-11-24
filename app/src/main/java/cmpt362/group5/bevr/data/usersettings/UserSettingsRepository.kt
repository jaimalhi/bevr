package cmpt362.group5.bevr.data.usersettings

import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Defines the set of operations that can be performed on the user settings.
 */
interface UserSettingsRepository {
    /**
     * Get a settings flow
     */
    fun getUserSettings(): Flow<UserSettings>

    /**
     * Updates the settings
     */
    suspend fun updateUserSettings(settings: UserSettings)
}