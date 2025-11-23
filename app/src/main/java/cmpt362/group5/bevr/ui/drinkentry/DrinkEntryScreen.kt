package cmpt362.group5.bevr.ui.drinkentry

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider.getUriForFile
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cmpt362.group5.bevr.BevrFileProvider
import cmpt362.group5.bevr.ui.theme.IconSize
import cmpt362.group5.bevr.ui.theme.Spacing
import coil3.compose.rememberAsyncImagePainter
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import java.io.File

private const val TAG = "DrinkEntryScreen"

private const val DRINK_IMAGE_PATH = "images/new_drink.bmp"

private val locationPermissions =
    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

private const val DEFAULT_RATING = 3

private lateinit var drinkImageFile: File

/**
 * The screen that allows the user to enter details about the drink they just had.
 */
@Composable
fun DrinkEntryScreen(viewModel: DrinkEntryViewModel = viewModel(factory = DrinkEntryViewModel.Factory)) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val locationClient = LocationServices.getFusedLocationProviderClient(context)

    var shouldShowLocationPermissionRationale by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState()

    var drinkLocation by rememberSaveable { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(drinkLocation) {
        drinkLocation?.let {
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(it, 15f))
        }
    }

    @SuppressLint("MissingPermission")
    fun updateLocation() {
        locationClient.lastLocation.addOnSuccessListener { location ->
            drinkLocation = LatLng(location.latitude, location.longitude)
        }.addOnFailureListener { exception ->
            Log.w(TAG, "Failed to get last location: $exception")
            Log.i(TAG, "Getting current location instead")
            locationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    drinkLocation = LatLng(location.latitude, location.longitude)
                }.addOnFailureListener { exception ->
                    Log.w(TAG, "Failed to get current location: $exception")
                }
        }
    }

    val multiplePermissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        if (locationPermissions.all { grants[it] == true }) {
            updateLocation()
        }
    }

    /**
     * Get location permission and current location
     */
    LaunchedEffect(Unit) {
        when {
            locationPermissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            } -> updateLocation()

            activity != null && ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> shouldShowLocationPermissionRationale = true

            else -> multiplePermissionRequestLauncher.launch(locationPermissions)
        }
    }

    var shouldShowCameraPermissionRationale by remember { mutableStateOf(false) }

    val imageUri by rememberSaveable {
        drinkImageFile = File(context.cacheDir, DRINK_IMAGE_PATH)
        drinkImageFile.parentFile?.mkdirs()
        drinkImageFile.createNewFile()
        val uri = getUriForFile(context, BevrFileProvider.AUTHORITY, drinkImageFile)
        mutableStateOf(uri)
    }

    var imageSaved by rememberSaveable { mutableStateOf(false) }

    var drinkName by rememberSaveable { mutableStateOf("") }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { imageSaved = it }

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(imageUri)
        }
    }

    val imageClickHandler = {
        Log.i(TAG, "Handling image click")
        when {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> cameraLauncher.launch(imageUri)

            activity != null && ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.CAMERA
            ) -> shouldShowCameraPermissionRationale = true

            else -> permissionRequestLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val drinkTypes by viewModel.drinkTypes.collectAsStateWithLifecycle(listOf())
    var selectedDrinkTypeId by rememberSaveable { mutableStateOf<Long?>(null) }

    if (shouldShowCameraPermissionRationale) {
        AlertDialog(
            title = { Text("Camera permissions needed") },
            text = { Text("We need camera permissions to take a picture of your drink.") },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowCameraPermissionRationale = false
                        permissionRequestLauncher.launch(Manifest.permission.CAMERA)
                    }
                ) { Text("Allow") }
            },
            dismissButton = {
                Button(
                    onClick = { shouldShowCameraPermissionRationale = false }
                ) { Text("No") }
            },
            onDismissRequest = { shouldShowCameraPermissionRationale = false },
        )
    }

    if (shouldShowLocationPermissionRationale) {
        AlertDialog(
            title = { Text("Location permissions needed") },
            text = { Text("We need location permissions to determine where you had your drink.") },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowCameraPermissionRationale = false
                        permissionRequestLauncher.launch(Manifest.permission.CAMERA)
                    }
                ) { Text("Allow") }
            },
            dismissButton = {
                Button(
                    onClick = { shouldShowCameraPermissionRationale = false }
                ) { Text("No") }
            },
            onDismissRequest = { shouldShowCameraPermissionRationale = false },
        )
    }

    var rating by rememberSaveable { mutableStateOf(DEFAULT_RATING) }

    Column(
        modifier = Modifier.padding(Spacing.Medium),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Log a new drink",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            label = { Text("What is it called?") },
            value = drinkName,
            placeholder = { Text("Coffee") },
            onValueChange = { drinkName = it },
            shape = MaterialTheme.shapes.medium
        )

        Text(
            text = "What type is it?",
            style = MaterialTheme.typography.labelLarge
        )
        LazyVerticalGrid(
            modifier = Modifier.wrapContentHeight(),
            columns = GridCells.Fixed(6)
        ) {
            items(drinkTypes) { drinkType ->
                Column(
                    modifier = Modifier
                        .padding(Spacing.Small)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    FilledIconToggleButton(drinkType.id == selectedDrinkTypeId, { selectedDrinkTypeId = drinkType.id }) {
                        Icon(
                            painter = painterResource(drinkType.icon.resId),
                            contentDescription = drinkType.name,
                            modifier = Modifier.size(IconSize.Standard),
                        )
                    }
                    Text(
                        text = drinkType.name,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }

        Text(
            text = "Give it a rating",
            style = MaterialTheme.typography.labelLarge
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            (1..5).forEach {
                OutlinedIconButton({ rating = if (it == rating) 0 else it }) {
                    Icon(
                        imageVector = if (it <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Rating star",
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxSize()
                    .weight(1f)
                    .clickable(onClick = imageClickHandler)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (imageSaved) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = imageClickHandler),
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "Image of the new drink to log.",
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Icon(
                        Icons.Default.CameraAlt,
                        modifier = Modifier.size(IconSize.Standard),
                        contentDescription = "Take a picture.",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { drinkLocation = it },
                ) {
                    drinkLocation?.let {
                        Marker(
                            state = rememberUpdatedMarkerState(it)
                        )
                    }
                }
            }
        }

        Button(
            enabled = selectedDrinkTypeId != null && drinkName.isNotBlank() && imageSaved && drinkLocation != null,
            onClick = {
                viewModel.addRecord(
                    drinkTypeId = selectedDrinkTypeId!!,
                    drinkName = drinkName,
                    location = drinkLocation!!,
                    drinkImageFile = drinkImageFile,
                    drinkRating = rating,
                )
                // Confirmation Toast
                Toast.makeText(
                    context,
                    "Beverage Logged!",
                    Toast.LENGTH_LONG
                ).show()
            }
        ) {
            Icon(Icons.Default.Add, "Add drink icon")
            Text("Log this drink")
        }
    }
}