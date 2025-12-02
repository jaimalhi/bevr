package cmpt362.group5.bevr.data.ai

data class AiIngredient(
    val item: String,
    val amount: String
)

data class AiServingDetails(
    val glassware: String,
    val garnish: String,
    val twistSuggestion: String
)

data class AiRecipe(
    val recipeName: String,
    val description: String,
    val prepTime: String,
    val ingredients: List<AiIngredient>,
    val steps: List<String>,
    val servingDetails: AiServingDetails
) {
    /**
     * Returns a nice paste-able text block for copy-to-clipboard.
     */
    fun toClipboardText(): String {
        val ingredientsText = ingredients.joinToString("\n") {
            "- ${it.amount} ${it.item}"
        }

        val stepsText = steps.mapIndexed { index, step ->
            "${index + 1}. $step"
        }.joinToString("\n")

        return buildString {
            appendLine(recipeName)
            appendLine()
            appendLine(description)
            appendLine()
            appendLine("Prep time: $prepTime")
            appendLine()
            appendLine("Ingredients:")
            appendLine(ingredientsText)
            appendLine()
            appendLine("Steps:")
            appendLine(stepsText)
            appendLine()
            appendLine("Serving details:")
            appendLine("Glassware: ${servingDetails.glassware}")
            appendLine("Garnish: ${servingDetails.garnish}")
            appendLine("Twist suggestion: ${servingDetails.twistSuggestion}")
        }.trim()
    }
}
