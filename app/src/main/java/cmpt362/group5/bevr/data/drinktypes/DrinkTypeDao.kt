package cmpt362.group5.bevr.data.drinktypes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinkTypeDao {
    @Query("SELECT * FROM DrinkType")
    fun getAll(): Flow<List<DrinkType>>

    @Insert
    suspend fun insertAll(vararg drinkTypes: DrinkType)

    @Insert
    suspend fun insert(drinkType: DrinkType): Long

    @Delete
    suspend fun delete(drinkType: DrinkType)
}