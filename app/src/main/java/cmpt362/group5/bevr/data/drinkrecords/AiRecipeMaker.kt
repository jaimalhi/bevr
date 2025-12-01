package cmpt362.group5.bevr.data.drinkrecords

import android.util.Log
import cmpt362.group5.bevr.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AiRecipeMaker {

    private val apiKey = BuildConfig.GEMINI_API_KEY

    fun generateRecipe(coroutineScope: CoroutineScope) {
        val model = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )

        coroutineScope.launch(Dispatchers.IO) {
            try {
                val prompt = "Generate a creative cocktail recipe."
                val response = model.generateContent(prompt)
                val output = response.text
                Log.d("AiRecipeMaker", "Generated Recipe: $output")
            } catch (e: Exception) {
                Log.e("AiRecipeMaker", "Error generating recipe: ", e)
            }
        }
    }
}
