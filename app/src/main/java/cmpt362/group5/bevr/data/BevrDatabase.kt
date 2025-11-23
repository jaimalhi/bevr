package cmpt362.group5.bevr.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordDao
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithTypeDao
import cmpt362.group5.bevr.data.drinktypes.DrinkType
import cmpt362.group5.bevr.data.drinktypes.DrinkTypeDao
import cmpt362.group5.bevr.data.typeconverters.DateConverter

@Database(entities = [DrinkRecord::class, DrinkType::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class BevrDatabase : RoomDatabase() {
    abstract fun drinkRecordDao(): DrinkRecordDao
    abstract fun drinkRecordWithTypeDao(): DrinkRecordWithTypeDao
    abstract fun drinkTypeDao(): DrinkTypeDao
}