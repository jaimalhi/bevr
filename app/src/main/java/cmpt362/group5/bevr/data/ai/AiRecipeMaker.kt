package cmpt362.group5.bevr.data.ai

import android.util.Log
import cmpt362.group5.bevr.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import org.json.JSONArray
import org.json.JSONObject

class AiRecipeMaker(
    apiKey: String = BuildConfig.GEMINI_API_KEY
) {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    /**
     * Suspend function to generate a recipe for the given drink type + name.
     * Returns Result<AiRecipe> for easy error handling.
     */
    suspend fun generateRecipe(
        type: String,
        drinkName: String
    ): Result<AiRecipe> {
        return try {
            val prompt = buildPrompt(type, drinkName)
            val response = model.generateContent(prompt)

            val rawText = response.text?.trim()
                ?: return Result.failure(IllegalStateException("Empty response from Gemini"))

            val cleaned = cleanupJsonText(rawText)
            val recipe = parseRecipeJson(cleaned)

            Result.success(recipe)
        } catch (e: Exception) {
            Log.e("AiRecipeMaker", "Error generating recipe", e)
            Result.failure(e)
        }
    }

    private fun buildPrompt(type: String, drinkName: String): String {
        // Your provided prompt, with [TYPE] and [DRINK_NAME] filled in
        return """
            **System Role:** You are a beverage API that outputs strictly structured JSON data.

            **Task:** Generate a beverage recipe based on the user's input variables.

            **Input Variables:**
            * Type: $type
            * Name: $drinkName
            * Flavor Profile: <generate this based on the recipe you create>
            * Serving Size: <serving size is always for a one person>

            **Logic Constraints:**
            1. If Type is Coffee, Tea, or Smoothie, the main ingredients MUST NOT contain alcohol.
            2. If Type is Cocktail, it MUST contain alcohol.
            3. The "twist" field is the only place where you can suggest deviations (e.g., adding alcohol to a coffee).

            **Output Format:**
            Return ONLY raw JSON (no markdown formatting like ```json or ```). The JSON must adhere to this schema:

            {
              "recipe_name": "String",
              "description": "String (A short, appetizing description)",
              "prep_time": "String (e.g., '5 mins')",
              "ingredients": [
                { "item": "String", "amount": "String (e.g. '2 oz')" }
              ],
              "steps": [
                "String (Step 1)",
                "String (Step 2)"
              ],
              "serving_details": {
                "glassware": "String",
                "garnish": "String",
                "twist_suggestion": "String"
              }
            }
        """.trimIndent()
    }

    /**
     * In case Gemini wraps JSON with stray backticks or text,
     * try to trim down to the JSON object.
     */
    private fun cleanupJsonText(raw: String): String {
        val trimmed = raw.trim().trim('`')
        // Very simple heuristic: find first '{' and last '}'.
        val start = trimmed.indexOf('{')
        val end = trimmed.lastIndexOf('}')
        return if (start != -1 && end != -1 && end >= start) {
            trimmed.substring(start, end + 1)
        } else {
            trimmed
        }
    }

    private fun parseRecipeJson(json: String): AiRecipe {
        val obj = JSONObject(json)

        val recipeName = obj.getString("recipe_name")
        val description = obj.getString("description")
        val prepTime = obj.getString("prep_time")

        val ingredientsArray: JSONArray = obj.getJSONArray("ingredients")
        val ingredients = mutableListOf<AiIngredient>()
        for (i in 0 until ingredientsArray.length()) {
            val ingObj = ingredientsArray.getJSONObject(i)
            ingredients += AiIngredient(
                item = ingObj.getString("item"),
                amount = ingObj.getString("amount")
            )
        }

        val stepsArray = obj.getJSONArray("steps")
        val steps = mutableListOf<String>()
        for (i in 0 until stepsArray.length()) {
            steps += stepsArray.getString(i)
        }

        val servingObj = obj.getJSONObject("serving_details")
        val servingDetails = AiServingDetails(
            glassware = servingObj.getString("glassware"),
            garnish = servingObj.getString("garnish"),
            twistSuggestion = servingObj.getString("twist_suggestion")
        )

        return AiRecipe(
            recipeName = recipeName,
            description = description,
            prepTime = prepTime,
            ingredients = ingredients,
            steps = steps,
            servingDetails = servingDetails
        )
    }
}
