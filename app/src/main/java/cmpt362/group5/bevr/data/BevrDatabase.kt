package cmpt362.group5.bevr.data

import androidx.room.Database
import androidx.room.RoomDatabase
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordDao

@Database(entities = [DrinkRecord::class], version = 1)
abstract class BevrDatabase : RoomDatabase() {
    abstract fun drinkRecordDao(): DrinkRecordDao
}