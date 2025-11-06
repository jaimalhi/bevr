package cmpt362.group5.bevr.data.drinkrecords

import kotlinx.coroutines.flow.Flow

/**
 * This concrete implementation should only be constructed and referenced by [cmpt362.group5.bevr.BevrApplication].
 */
class DrinkRecordRepositoryImpl(private val drinkRecordDao: DrinkRecordDao) : DrinkRecordRepository {

    private val drinkRecords = drinkRecordDao.getAll()

    override fun getDrinkRecords(): Flow<List<DrinkRecord>> = drinkRecords

    override suspend fun createDrinkRecord(drinkRecord: DrinkRecord) {
        drinkRecordDao.insertAll(drinkRecord)
    }

    override suspend fun deleteDrinkRecord(drinkRecord: DrinkRecord) {
        drinkRecordDao.delete(drinkRecord)
    }

}