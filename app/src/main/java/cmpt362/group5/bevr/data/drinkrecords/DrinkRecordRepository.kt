package cmpt362.group5.bevr.data.drinkrecords

import kotlinx.coroutines.flow.Flow

/**
 * Defines the operations that can be performed on drink records
 */
interface DrinkRecordRepository {
    fun getDrinkRecordsWithType(): Flow<List<DrinkRecordWithType>>
    fun getDrinkRecordWithType(drinkRecordId: Long): Flow<DrinkRecordWithType>
    fun getDrinkRecord(drinkRecordId: Long): Flow<DrinkRecord>
    suspend fun createDrinkRecord(drinkRecord: DrinkRecord): Long
    suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord)
}