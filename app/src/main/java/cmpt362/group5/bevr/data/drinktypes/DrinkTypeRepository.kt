package cmpt362.group5.bevr.data.drinktypes

import kotlinx.coroutines.flow.Flow

interface DrinkTypeRepository {
    fun getDrinkTypes(): Flow<List<DrinkType>>
}