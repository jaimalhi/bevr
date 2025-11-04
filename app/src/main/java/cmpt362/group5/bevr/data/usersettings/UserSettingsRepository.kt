package cmpt362.group5.bevr.data.usersettings

import kotlinx.coroutines.flow.Flow

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