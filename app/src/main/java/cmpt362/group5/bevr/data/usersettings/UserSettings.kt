package cmpt362.group5.bevr.data.usersettings

// Represents one drink type definition used *everywhere*
data class BeverageDefinition(
    val key: String,           // canonical key used in settings + charts
    val label: String,         // UI label
    val dbNames: Set<String>   // names appearing in DrinkType.name
)

/**
 * We now map the 12 UI drink categories into 5 canonical groups:
 *
 * - COFFEE → coffee
 * - COCO → coffee  (hot chocolate style)
 *
 * - TEA → tea
 *
 * - SODA → juice   (soft drinks go under non-alcoholic sweet beverages)
 * - JUICE → juice
 * - SMOOTHIE → juice
 * - SHAKES → juice
 *
 * - BOBA → boba
 *
 * - BEER → liquor
 * - WINE → liquor
 * - SPIRITS → liquor
 * - COCKTAIL → liquor
 *
 * These dbNames allow our Profile screen to categorize any possible DrinkType.name
 */
val BEVERAGE_DEFINITIONS = listOf(
    BeverageDefinition(
        key = "coffee",
        label = "Coffee",
        dbNames = setOf(
            // Traditional coffee
            "coffee", "espresso", "cold brew", "latte", "mocha", "cappuccino",
            "americano", "macchiato", "flat white", "cortado", "affogato",
            "frappuccino", "iced coffee", "nitro cold brew", "café au lait",
            "turkish coffee", "vietnamese coffee", "café con leche",

            // Mapped UI categories:
            "COFFEE", "Coffee", "coffee",
            "COCO", "Coco", "coco", "hot chocolate", "hot coco",
            "COCOA", "Cocoa", "cocoa"
        )
    ),

    BeverageDefinition(
        key = "tea",
        label = "Tea",
        dbNames = setOf(
            "tea", "green tea", "black tea", "oolong tea", "white tea",
            "herbal tea", "chai", "matcha", "earl grey", "english breakfast",
            "jasmine tea", "chamomile", "peppermint tea", "rooibos",
            "iced tea", "thai tea", "yerba mate", "pu-erh tea", "darjeeling",

            // UI category
            "TEA", "Tea", "tea"
        )
    ),

    BeverageDefinition(
        key = "juice",
        label = "Juice",
        dbNames = setOf(
            // Classic juice
            "juice", "orange juice", "apple juice", "grapefruit juice",
            "cranberry juice", "pineapple juice", "grape juice", "tomato juice",
            "lemon juice", "lime juice", "pomegranate juice", "mango juice",
            "carrot juice", "vegetable juice", "fresh juice", "pressed juice",
            "fruit juice",

            // UI categories
            "JUICE", "Juice", "juice",
            "SMOOTHIE", "Smoothie", "smoothie",
            "SHAKE", "Shake", "shake", "milkshake",

            // Soft drinks
            "SODA", "Soda", "soda", "cola", "soft drink", "pop"
        )
    ),

    BeverageDefinition(
        key = "liquor",
        label = "Liquor",
        dbNames = setOf(
            // Alcoholic beverages
            "liquor", "alcohol", "beer", "wine", "vodka", "whiskey", "rum",
            "gin", "tequila", "bourbon", "scotch", "brandy", "cognac",
            "champagne", "prosecco", "sake", "soju", "cocktail", "martini",
            "margarita", "mojito", "sangria", "cider", "ale", "lager",
            "stout", "ipa", "red wine", "white wine", "rosé", "rose", "port",
            "sherry", "vermouth", "absinthe", "liqueur", "schnapps",

            // UI categories
            "BEER", "Beer", "beer",
            "WINE", "Wine", "wine",
            "SPIRITS", "Spirits", "spirits",
            "COCKTAIL", "Cocktails", "cocktails"
        )
    ),

    BeverageDefinition(
        key = "boba",
        label = "Bubble Tea",
        dbNames = setOf(
            "boba", "bubble tea", "milk tea", "pearl milk tea", "boba tea",
            "tapioca tea", "thai milk tea", "taro milk tea", "brown sugar boba",
            "fruit tea", "cheese tea", "matcha boba", "brown sugar milk tea",
            "honeydew milk tea", "jasmine milk tea",

            // UI category
            "BOBA", "Boba", "boba"
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
     * The set of active beverage groups shown across the app.
     * (coffee, tea, juice, liquor, boba)
     */
    val activeBeverages: Set<String> = DEFAULT_ACTIVE_BEVERAGES,

    /**
     * Future theming support.
     */
    val selectedTheme: String = "default"
)
