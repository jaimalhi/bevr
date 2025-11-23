package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkRecordWithTypeDao {
    @Query("SELECT * FROM DrinkRecord")
    fun getAll(): Flow<List<DrinkRecordWithType>>

    @Query("SELECT * FROM DrinkRecord WHERE id = :id")
    fun get(id: Long): Flow<DrinkRecordWithType>
}