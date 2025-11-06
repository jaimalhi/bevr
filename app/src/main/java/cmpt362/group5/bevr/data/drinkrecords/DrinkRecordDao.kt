package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkRecordDao {
    @Query("SELECT * FROM drink_record")
    fun getAll(): Flow<List<DrinkRecord>>

    @Insert
    suspend fun insertAll(vararg drinkRecords: DrinkRecord)

    @Delete
    suspend fun delete(drinkRecord: DrinkRecord)
}