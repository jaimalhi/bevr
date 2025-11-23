package cmpt362.group5.bevr.data.drinkrecords

import kotlinx.coroutines.flow.Flow

/**
 * This concrete implementation should only be constructed and referenced by [cmpt362.group5.bevr.BevrApplication].
 */
class DrinkRecordRepositoryImpl(
    private val drinkRecordDao: DrinkRecordDao,
    private val drinkRecordWithTypeDao: DrinkRecordWithTypeDao,
) : DrinkRecordRepository {

    private val drinkRecordsWithType = drinkRecordWithTypeDao.getAll()

    override fun getDrinkRecordsWithType(): Flow<List<DrinkRecordWithType>> = drinkRecordsWithType
    override fun getDrinkRecordWithType(drinkRecordId: Long): Flow<DrinkRecordWithType> =
        drinkRecordWithTypeDao.get(drinkRecordId)

    override fun getDrinkRecord(drinkRecordId: Long): Flow<DrinkRecord> =
        drinkRecordDao.get(drinkRecordId)

    override suspend fun createDrinkRecord(drinkRecord: DrinkRecord): Long {
        return drinkRecordDao.insert(drinkRecord)
    }

    override suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord) {
        drinkRecordDao.delete(drinkRecord)
    }

}