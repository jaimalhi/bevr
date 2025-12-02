package cmpt362.group5.bevr.ui.drinklog

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.BevrApplication
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    drinkRecordId: Long,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as BevrApplication
    val viewModel: RecipeViewModel = viewModel(
        factory = RecipeViewModel.Factory(
            drinkRecordId = drinkRecordId,
            recordRepo = app.container.drinkRecordRepository,
            aiRecipeMaker = app.container.aiRecipeMaker
        )
    )

    val uiState by viewModel.uiState.collectAsState()
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    // Handle error: toast + go back to log
    LaunchedEffect(uiState.error) {
        uiState.error?.let { msg ->
            Toast.makeText(context, "Error generating recipe: $msg", Toast.LENGTH_LONG).show()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generated Recipe") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.recipe != null -> {
                    val recipe = uiState.recipe!!
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // STATIC HEADER: title + desc + prep time
                        Text(
                            text = recipe.recipeName,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = recipe.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Prep time: ${recipe.prepTime}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // SCROLLABLE SECTION: ingredients + steps + serving
                        Column(
                            modifier = Modifier
                                .weight(1f) // take all remaining space
                                .verticalScroll(scrollState)
                        ) {
                            // Ingredients
                            Text(
                                text = "Ingredients",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            recipe.ingredients.forEach { ing ->
                                Text("- ${ing.amount} ${ing.item}")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Steps
                            Text(
                                text = "Steps",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            recipe.steps.forEachIndexed { index, step ->
                                Text("${index + 1}. $step")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Serving details
                            Text(
                                text = "Serving Details",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Glassware: ${recipe.servingDetails.glassware}")
                            Text("Garnish: ${recipe.servingDetails.garnish}")
                            Text("Twist: ${recipe.servingDetails.twistSuggestion}")

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // STATIC BOTTOM: Copy button
                        Button(
                            onClick = {
                                val text = recipe.toClipboardText()
                                scope.launch {
                                    val clipData = ClipData.newPlainText("Bevr recipe", text)
                                    val clipEntry = clipData.toClipEntry()
                                    clipboard.setClipEntry(clipEntry)
                                }
                                Toast
                                    .makeText(context, "Recipe copied to clipboard", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Copy Recipe")
                        }
                    }
                }

                else -> {
                    // No loading, no recipe, no error - nothing to render
                }
            }
        }
    }
}
