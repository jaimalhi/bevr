package cmpt362.group5.bevr.data.usersettings

// Represents one drink type definition used *everywhere*
data class BeverageDefinition(
    val key: String,           // canonical key used in settings + charts
    val label: String,         // UI label
    val dbNames: Set<String>   // names appearing in DrinkType.name
)

// Centralized definitions list
val BEVERAGE_DEFINITIONS = listOf(
    BeverageDefinition(
        key = "coffee",
        label = "Coffee",
        dbNames = setOf(
            "coffee", "espresso", "cold brew", "latte", "mocha", "cappuccino",
            "americano", "macchiato", "flat white", "cortado", "affogato",
            "frappuccino", "iced coffee", "nitro cold brew", "café au lait",
            "turkish coffee", "vietnamese coffee", "café con leche"
        )
    ),
    BeverageDefinition(
        key = "tea",
        label = "Tea",
        dbNames = setOf(
            "tea", "green tea", "black tea", "oolong tea", "white tea",
            "herbal tea", "chai", "matcha", "earl grey", "english breakfast",
            "jasmine tea", "chamomile", "peppermint tea", "rooibos",
            "iced tea", "thai tea", "yerba mate", "pu-erh tea", "darjeeling"
        )
    ),
    BeverageDefinition(
        key = "juice",
        label = "Juice",
        dbNames = setOf(
            "juice", "orange juice", "apple juice", "grapefruit juice",
            "cranberry juice", "pineapple juice", "grape juice", "tomato juice",
            "lemon juice", "lime juice", "pomegranate juice", "mango juice",
            "carrot juice", "vegetable juice", "smoothie", "fresh juice",
            "pressed juice", "fruit juice"
        )
    ),
    BeverageDefinition(
        key = "liquor",
        label = "Liquor",
        dbNames = setOf(
            "liquor", "alcohol", "beer", "wine", "vodka", "whiskey", "rum",
            "gin", "tequila", "bourbon", "scotch", "brandy", "cognac",
            "champagne", "prosecco", "sake", "soju", "cocktail", "martini",
            "margarita", "mojito", "sangria", "cider", "ale", "lager",
            "stout", "IPA", "red wine", "white wine", "rosé", "rose", "port",
            "sherry", "vermouth", "absinthe", "liqueur", "schnapps"
        )
    ),
    BeverageDefinition(
        key = "boba",
        label = "Bubble Tea",
        dbNames = setOf(
            "boba", "bubble tea", "milk tea", "pearl milk tea", "boba tea",
            "tapioca tea", "thai milk tea", "taro milk tea", "brown sugar boba",
            "fruit tea", "cheese tea", "matcha boba", "brown sugar milk tea",
            "honeydew milk tea", "jasmine milk tea"
        )
    )
)

// Default settings
val DEFAULT_ACTIVE_BEVERAGES: Set<String> =
    BEVERAGE_DEFINITIONS.map { it.key }.toSet()

const val DEFAULT_AVATAR_ID = 0

/**
 * Contains all user settings values.
 */
data class UserSettings(
    val displayName: String = "Guest",
    val avatarId: Int = DEFAULT_AVATAR_ID,
    /**
     * The set of drink type keys that are active/visible in the app
     * (e.g. "coffee", "tea", "juice", "liquor", "boba").
     */
    val activeBeverages: Set<String> = DEFAULT_ACTIVE_BEVERAGES,
    /**
     * The currently selected theme (matches one of the drink keys).
     * Starts as "default" bevr theme.
     */
    val selectedTheme: String = "default"
)
