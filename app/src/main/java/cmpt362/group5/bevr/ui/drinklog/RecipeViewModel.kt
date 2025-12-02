package cmpt362.group5.bevr.ui.drinklog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cmpt362.group5.bevr.data.ai.AiRecipe
import cmpt362.group5.bevr.data.ai.AiRecipeMaker
import cmpt362.group5.bevr.data.drinkrecords.DrinkRecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class RecipeUiState(
    val isLoading: Boolean = true,
    val recipe: AiRecipe? = null,
    val error: String? = null
)

class RecipeViewModel(
    private val drinkRecordId: Long,
    private val drinkRecordRepository: DrinkRecordRepository,
    private val aiRecipeMaker: AiRecipeMaker
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState

    init {
        loadRecipe()
    }

    private fun loadRecipe() {
        viewModelScope.launch {
            try {
                _uiState.value = RecipeUiState(isLoading = true)

                // Get the drink + type
                val recordWithType = drinkRecordRepository
                    .getDrinkRecordWithType(drinkRecordId)
                    .first()

                val typeName = recordWithType.drinkType.name
                val drinkName = recordWithType.drinkRecord.name

                val result = aiRecipeMaker.generateRecipe(
                    type = typeName,
                    drinkName = drinkName
                )

                result
                    .onSuccess { recipe ->
                        _uiState.value = RecipeUiState(
                            isLoading = false,
                            recipe = recipe,
                            error = null
                        )
                    }
                    .onFailure { e ->
                        _uiState.value = RecipeUiState(
                            isLoading = false,
                            recipe = null,
                            error = e.message ?: "Unknown error"
                        )
                    }

            } catch (e: Exception) {
                _uiState.value = RecipeUiState(
                    isLoading = false,
                    recipe = null,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    class Factory(
        private val drinkRecordId: Long,
        private val recordRepo: DrinkRecordRepository,
        private val aiRecipeMaker: AiRecipeMaker
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipeViewModel(drinkRecordId, recordRepo, aiRecipeMaker) as T
        }
    }
}
