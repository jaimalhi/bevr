package cmpt362.group5.bevr.data.usersettings

/**
 * Contains all user settings values.
 */
const val DEFAULT_AVATAR_ID = 0

data class UserSettings(
    val displayName: String = "Guest",
    val avatarId: Int = DEFAULT_AVATAR_ID,
    /**
     * The set of drink type keys that are active/visible in the app
     * (e.g. "coffee", "tea", "juice", "liquor", "boba").
     */
    val activeBeverages: Set<String> = emptySet(),
)
