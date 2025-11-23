package cmpt362.group5.bevr.data.drinktypes

import androidx.annotation.DrawableRes
import cmpt362.group5.bevr.R

/**
 * Storage enum class for allowed icons.
 * Constraining the possible icons here allows us to simplify icon storage since we only have to
 * handle known icons.
 */
enum class DrinkTypeIcon(
    @param:DrawableRes val resId: Int
) {
    COFFEE(R.drawable.coffee_bean_filled_roast_brew_svgrepo_com),
    TEA(R.drawable.tea_leaf_svgrepo_com),
    COCO(R.drawable.chocolate_svgrepo_com),
    SODA(R.drawable.soda_straw_svgrepo_com),

    JUICE(R.drawable.orange_svgrepo_com),
    SMOOTHIE(R.drawable.blender_svgrepo_com),
    SHAKES(R.drawable.milkshake_svgrepo_com),
    BOBA(R.drawable.bubble_tea_icon),

    BEER(R.drawable.beer_mug_svgrepo_com),
    WINE(R.drawable.wine_fill_svgrepo_com),
    SPIRITS(R.drawable.vodka_svgrepo_com),
    COCKTAIL(R.drawable.cocktail_svgrepo_com),
}