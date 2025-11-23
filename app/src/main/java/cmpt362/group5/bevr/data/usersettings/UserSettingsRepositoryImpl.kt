package cmpt362.group5.bevr.data.usersettings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This concrete implementation should only be constructed and referenced by [cmpt362.group5.bevr.BevrApplication].
 */
class UserSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : UserSettingsRepository {

    /**
     * The keys that are used to get values from the preferences datastore
     */
    private object Keys {
        val DISPLAY_NAME = stringPreferencesKey("display_name")
        val AVATAR_ID = intPreferencesKey("avatar_id")
        val ACTIVE_BEVERAGES = stringSetPreferencesKey("active_beverages")
    }

    /**
     * Initialize and keep a user settings flow.
     * Updates are emitted into this flow and collected by users of [UserSettingsRepository].
     */
    private val userSettings: Flow<UserSettings> = dataStore.data.map { preferences ->
        val displayName = preferences[Keys.DISPLAY_NAME] ?: "Guest"
        val avatarId = preferences[Keys.AVATAR_ID] ?: DEFAULT_AVATAR_ID
        val activeBeverages = preferences[Keys.ACTIVE_BEVERAGES] ?: emptySet()

        UserSettings(
            displayName = displayName,
            avatarId = avatarId,
            activeBeverages = activeBeverages,
        )
    }

    override fun getUserSettings(): Flow<UserSettings> = userSettings

    override suspend fun updateUserSettings(settings: UserSettings) {
        dataStore.edit { preferences ->
            preferences[Keys.DISPLAY_NAME] = settings.displayName
            preferences[Keys.AVATAR_ID] = settings.avatarId
            preferences[Keys.ACTIVE_BEVERAGES] = settings.activeBeverages
        }
    }
}
