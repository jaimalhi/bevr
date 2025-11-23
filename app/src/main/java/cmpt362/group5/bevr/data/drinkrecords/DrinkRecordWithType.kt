package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Embedded
import androidx.room.Relation
import cmpt362.group5.bevr.data.drinktypes.DrinkType

data class DrinkRecordWithType(
    @Embedded
    val drinkRecord: DrinkRecord,
    @Relation(parentColumn = "drinkTypeId", entityColumn = "id")
    val drinkType: DrinkType,
)