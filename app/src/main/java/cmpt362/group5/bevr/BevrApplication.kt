package cmpt362.group5.bevr

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import cmpt362.group5.bevr.data.BevrDatabase
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepositoryImpl
import cmpt362.group5.bevr.data.drinktypes.DrinkTypeRepositoryImpl
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepositoryImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Date


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
        override val userSettingsRepository by lazy {
            UserSettingsRepositoryImpl(
                userSettingsDataStore
            )
        }
        override val drinkRecordRepository by lazy {
            DrinkRecordRepositoryImpl(
                database.drinkRecordDao(),
                database.drinkRecordWithTypeDao()
            )
        }
        override val drinkTypeRepository by lazy { DrinkTypeRepositoryImpl(database.drinkTypeDao()) }
    }


    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            context = applicationContext,
            klass = BevrDatabase::class.java,
            name = DATABASE_NAME
        )
            .createFromAsset("bevr.db")
            .build()

        seedDummyDrinkData() // TODO: REMOVE BEFORE MERGING TO MAIN
    }

    // TODO: REMOVE BEFORE MERGING TO MAIN
    @OptIn(DelicateCoroutinesApi::class)
    private fun seedDummyDrinkData() {
        // Very basic example — in real code you'd use coroutines properly.
        GlobalScope.launch {
            val repo = container.drinkRecordRepository

            // If you have a way to list drink types and find ids:
            val allRecords = repo.getDrinkRecordsWithType().firstOrNull().orEmpty()
            if (allRecords.isNotEmpty()) return@launch // already seeded

            // Otherwise you may need direct DAOs via `database.drinkRecordDao()` etc.
            val now = Date()
            // Example using hard-coded drinkTypeIds (adjust based on your DB):
            repo.createDrinkRecord(DrinkRecord(
                drinkTypeId = 1, timestamp = now,
                id = 0,
                name = "",
                longitude = 0.0,
                latitude = 0.0,
                rating = 2
            ))
            repo.createDrinkRecord(DrinkRecord(
                drinkTypeId = 2, timestamp = now,
                id = 0,
                name = "",
                longitude = 0.0,
                latitude = 0.0,
                rating = 2
            ))
            repo.createDrinkRecord(DrinkRecord(
                drinkTypeId = 3, timestamp = now,
                id = 0,
                name = "",
                longitude = 0.0,
                latitude = 0.0,
                rating = 2
            ))
        }
    }
}