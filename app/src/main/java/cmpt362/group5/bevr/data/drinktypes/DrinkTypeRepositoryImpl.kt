package cmpt362.group5.bevr.data.drinktypes

class DrinkTypeRepositoryImpl(private val drinkTypeDao: DrinkTypeDao) : DrinkTypeRepository {
    override fun getDrinkTypes() = drinkTypeDao.getAll()
}