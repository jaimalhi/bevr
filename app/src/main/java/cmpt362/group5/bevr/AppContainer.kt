package cmpt362.group5.bevr

import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import cmpt362.group5.bevr.data.usersettings.UserSettingsRepository

/**
 * Contains data sources and various values intended to be used by the entire application and accessible anywhere in the application.
 * Avoid using concrete implementations, prefer interfaces.
 * Use this interface to provide guarantees on which data access objects will be available to use throughout the application.
 */
interface AppContainer {
    val userSettingsRepository: UserSettingsRepository
    val drinkRecordRepository: DrinkRecordRepository
}