package cmpt362.group5.bevr

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import cmpt362.group5.bevr.data.BevrDatabase
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordDao
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordWithTypeDao
import cmpt362.group5.bevr.data.drinktypes.DrinkType
import cmpt362.group5.bevr.data.drinktypes.DrinkTypeDao
import cmpt362.group5.bevr.data.drinktypes.DrinkTypeIcon
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var drinkTypeDao: DrinkTypeDao
    private lateinit var drinkRecordDao: DrinkRecordDao
    private lateinit var drinkRecordWithTypeDao: DrinkRecordWithTypeDao

    private lateinit var db: BevrDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BevrDatabase::class.java).build()
        drinkTypeDao = db.drinkTypeDao()
        drinkRecordDao = db.drinkRecordDao()
        drinkRecordWithTypeDao = db.drinkRecordWithTypeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun createDrinkTypes() = runBlocking {
        drinkTypeDao.insertAll(*DrinkTypeIcon.entries.map { DrinkType(name = it.name, icon = it) }.toTypedArray())


        val expected = DrinkTypeIcon.entries.first()
        val all = drinkTypeDao.getAll().first()
        val first = all.first()

        assertEquals(DrinkTypeIcon.entries.size, all.size)
        assertEquals(expected.name, first.name)
        assertEquals(expected, first.icon)
    }

    @Test
    fun createDrinkRecord() = runBlocking {
        val type = DrinkType(name = "Coffee", icon = DrinkTypeIcon.COFFEE)
        val id = drinkTypeDao.insert(type)

        val expected = DrinkRecord(drinkTypeId = id, timestamp = Date())
        drinkRecordDao.insertAll(expected)

        val first = drinkRecordDao.getAll().first().first()
        assertEquals(expected.timestamp, first.timestamp)
        assertEquals(expected.drinkTypeId, first.drinkTypeId)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun createDrinkRecordWithNonExistentType() = runBlocking {
        val nonExistingDrinkTypeId = 21L
        val expected = DrinkRecord(drinkTypeId = nonExistingDrinkTypeId, timestamp = Date())
        drinkRecordDao.insert(expected)
        Unit
    }
}