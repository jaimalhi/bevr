package cmpt362.group5.bevr.data.drinkrecords

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ImagePart
import com.google.firebase.ai.type.ResponseModality
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AiRecipeMaker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")

        lifecycleScope.launch {
            try {
                val prompt = "Write a short Kotlin function that adds two numbers."
                val response = model.generateContent(prompt)
                val output = response.text  // may be null if no text output
                Log.d("GeminiExample", "Got response: $output")
            } catch (e: Exception) {
                Log.e("GeminiExample", "Error calling Gemini", e)
            }
        }
    }
}