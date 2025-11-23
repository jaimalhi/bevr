package cmpt362.group5.bevr.data.images

import android.net.Uri
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import java.io.File

interface DrinkRecordImageRepository {
    /**
     * Must give a valid existing drink record
     */
    fun getImageUriForDrinkRecord(drinkRecord: DrinkRecord): Uri

    /**
     * Persists an image file for a valid drink record
     */
    suspend fun saveImageForDrinkRecord(drinkRecord: DrinkRecord, imageFile: File)
}