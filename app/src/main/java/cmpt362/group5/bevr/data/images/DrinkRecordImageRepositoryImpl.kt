package cmpt362.group5.bevr.data.images

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import cmpt362.group5.bevr.BevrFileProvider
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord
import java.io.File

class DrinkRecordImageRepositoryImpl(private val context: Context) : DrinkRecordImageRepository {

    /**
     * Directory containing all the images
     */
    private val root = File(context.filesDir, "drink_record_images")

    override fun getImageUriForDrinkRecord(drinkRecord: DrinkRecord): Uri =
        FileProvider.getUriForFile(
            context,
            BevrFileProvider.AUTHORITY,
            File(root, "${drinkRecord.id}.bmp")
        )

    override suspend fun saveImageForDrinkRecord(
        drinkRecord: DrinkRecord,
        imageFile: File
    ) {
        val target = File(root, "${drinkRecord.id}.bmp")
        target.parentFile?.mkdirs()
        target.createNewFile()
        imageFile.copyTo(target, overwrite = true)
    }
}