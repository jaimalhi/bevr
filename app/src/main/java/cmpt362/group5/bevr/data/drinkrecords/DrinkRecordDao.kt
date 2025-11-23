package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkRecordDao {
    @Query("SELECT * FROM DrinkRecord")
    fun getAll(): Flow<List<DrinkRecord>>

    @Query("SELECT * FROM DrinkRecord WHERE id = :id")
    fun get(id: Long): Flow<DrinkRecord>

    @Insert
    suspend fun insertAll(vararg drinkRecords: DrinkRecord)

    @Insert
    suspend fun insert(drinkRecord: DrinkRecord): Long

    @Delete
    suspend fun delete(drinkRecord: DrinkRecord)
}