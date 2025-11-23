package cmpt362.group5.bevr.data.typeconverters

import androidx.room.TypeConverter
import java.util.Date

/**
 * Enable automatic conversions from instant in time to milliseconds since epoche and back.
 */
class DateConverter {
    @TypeConverter
    fun convertEpochMillisecondsToDate(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun convertDateToEpocheMilliseconds(date: Date?): Long? = date?.time
}