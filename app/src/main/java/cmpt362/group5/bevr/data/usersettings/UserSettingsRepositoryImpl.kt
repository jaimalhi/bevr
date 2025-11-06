package cmpt362.group5.bevr.data.usersettings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * This concrete implementation should only be constructed and referenced by [cmpt362.group5.bevr.BevrApplication].
 */
class UserSettingsRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    UserSettingsRepository {
    /**
     * The keys that are used to get values from the preferences datastore
     */
    private object Keys {
        /**
         * Example only TODO: remove
         */
        val STUB_KEY = intPreferencesKey("stub")
    }

    /**
     * Initialize and keep a user settings flow.
     * Updates are emitted into this flow and collected by users of [UserSettingsRepository].
     */
    private val userSettings = dataStore.data.map {
        UserSettings(it[Keys.STUB_KEY] ?: 0) // TODO: Example data access only. Remove.
    }

    override fun getUserSettings(): Flow<UserSettings> = userSettings

    override suspend fun updateUserSettings(settings: UserSettings) {
        dataStore.edit { preferences ->
            preferences[Keys.STUB_KEY] = settings.stub // TODO: Example data update only. Remove.
        }
    }
}