package cmpt362.group5.bevr.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * The screen that allows the user to configure the application and personalize it.
 *
 * - Avatar + name header (both editable via dialogs)
 * - Checklist of drink types (active beverages)
 * - "Save changes" button that only enables when something has changed
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory),
) {
    val uiState by settingsViewModel.uiState.collectAsState()

    var showAvatarDialog by remember { mutableStateOf(false) }
    var showNameDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header: Avatar + Name
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Avatar with edit icon
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable { showAvatarDialog = true },
                                contentAlignment = Alignment.Center
                            ) {
                                // Simple avatar: show first letter or generic
                                val initial = uiState.displayName.firstOrNull()?.uppercase()
                                    ?: "A"
                                Text(
                                    text = initial.toString(),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                            IconButton(onClick = { showAvatarDialog = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit avatar")
                            }
                        }

                        // Name with edit icon
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = uiState.displayName.ifBlank { "Tap to set name" },
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.clickable { showNameDialog = true }
                            )
                            IconButton(onClick = { showNameDialog = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit name")
                            }
                        }
                    }
                }

                // Active beverage checklist
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Drink types to show",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        SettingsViewModel.BEVERAGE_OPTIONS.forEach { option ->
                            val checked = option.key in uiState.activeBeverages
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        settingsViewModel.onBeverageToggled(option.key)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = {
                                        settingsViewModel.onBeverageToggled(option.key)
                                    }
                                )
                                Text(
                                    text = option.label,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Save button
                Button(
                    onClick = {
                        settingsViewModel.onSave()
                        onNavigateBack()
                    },
                    enabled = uiState.isDirty,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Save changes")
                }
            }
        }
    }

    // Name edit dialog
    if (showNameDialog) {
        var nameInput by remember(uiState.displayName) {
            mutableStateOf(uiState.displayName)
        }

        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Edit name") },
            text = {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        settingsViewModel.onDisplayNameChange(nameInput)
                        showNameDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNameDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Avatar selection dialog
    if (showAvatarDialog) {
        AlertDialog(
            onDismissRequest = { showAvatarDialog = false },
            title = { Text("Choose avatar") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Select one of the avatar styles below.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        (0 until SettingsViewModel.AVATAR_COUNT).forEach { index ->
                            val isSelected = uiState.avatarId == index
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .clickable {
                                        settingsViewModel.onAvatarSelected(index)
                                        showAvatarDialog = false
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAvatarDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
