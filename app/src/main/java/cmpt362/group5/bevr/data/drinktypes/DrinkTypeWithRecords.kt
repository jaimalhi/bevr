package cmpt362.group5.bevr.data.drinktypes

import androidx.room.Embedded
import androidx.room.Relation
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecord

/**
 * Drink type and record relationship
 */
data class DrinkTypeWithRecords(
    @Embedded
    val drinkType: DrinkType,
    @Relation(parentColumn = "id", entityColumn = "drinkTypeId")
    val drinkRecords: List<DrinkRecord>
)