package cmpt362.group5.bevr.data.drinktypes

import androidx.room.TypeConverter

class DrinkTypeIconConverter {
    @TypeConverter
    fun convertIconToName(drinkTypeIcon: DrinkTypeIcon?) = drinkTypeIcon?.name

    @TypeConverter
    fun convertNameToIcon(drinkTypeIconName: String?) =
        drinkTypeIconName?.let { DrinkTypeIcon.valueOf(it) }
}