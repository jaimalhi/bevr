package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import cmpt362.group5.bevr.data.drinktypes.DrinkType
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(DrinkType::class, ["id"], ["drinkTypeId"])
    ]
)
data class DrinkRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val drinkTypeId: Long,
    val timestamp: Date = Date(),
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val rating: Int
)
