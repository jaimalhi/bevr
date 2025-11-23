package cmpt362.group5.bevr.data.drinktypes

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(indices = [Index("name", unique = true)])
@TypeConverters(DrinkTypeIconConverter::class)
data class DrinkType(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    /**
     * Shown to the user
     */
    val name: String,
    /**
     * Shown to the user
     */
    val icon: DrinkTypeIcon
)
