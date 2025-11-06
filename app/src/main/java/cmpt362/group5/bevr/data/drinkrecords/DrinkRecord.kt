package cmpt362.group5.bevr.data.drinkrecords

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_record")
data class DrinkRecord(@PrimaryKey val id: Int)
