package cmpt362.group5.bevr.data.usersettings

/**
 * All drink keys used across the app.
 */
val DEFAULT_ACTIVE_BEVERAGES: Set<String> =
    setOf("coffee", "tea", "juice", "liquor", "boba")
/**
 * Contains all user settings values.
 */
const val DEFAULT_AVATAR_ID = 0

data class UserSettings(
    val displayName: String = "Guest",
    val avatarId: Int = DEFAULT_AVATAR_ID,
    /**
     * The set of drink type keys that are active/visible in the app
     */
    val activeBeverages: Set<String> = DEFAULT_ACTIVE_BEVERAGES,
)
