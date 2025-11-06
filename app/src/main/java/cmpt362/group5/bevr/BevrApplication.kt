package cmpt362.group5.bevr

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import cmpt362.group5.bevr.data.BevrDatabase
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepositoryImpl
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepositoryImpl


class BevrApplication : Application() {
    companion object {
        private const val USER_SETTINGS_PREFERENCE_NAME = "user-settings"
        private const val DATABASE_NAME = "bevr"
    }

    /**
     * Shared preferences data store EXCLUSIVELY to be used through the [UserSettingsRepository].
     */
    private val userSettingsDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS_PREFERENCE_NAME)

    private lateinit var database: BevrDatabase

    /**
     * A container that holds data access objects and can be accessed from anywhere in the application.
     */
    val container: AppContainer = object : AppContainer {
        override val userSettingsRepository by lazy { UserSettingsRepositoryImpl(userSettingsDataStore) }
        override val drinkRecordRepository by lazy { DrinkRecordRepositoryImpl(database.drinkRecordDao()) }
    }


    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            context = applicationContext, klass = BevrDatabase::class.java, name = DATABASE_NAME
        ).build()
    }
}