package cmpt362.group5.bevr.ui.locations

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

/**
 * The screen that shows a map of all locations where the drinks were had.
 */
@Composable
fun LocationsScreen(viewModel: LocationsViewModel = viewModel(factory = LocationsViewModel.Factory)) {
    val drinkRecords by viewModel.drinkRecords.observeAsState(emptyList())
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val locations by viewModel.locationFlow.observeAsState(emptyList())
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                }
            }
        }
    }

    LaunchedEffect(userLocation, locations) {
        val target = locations.lastOrNull() ?: userLocation
        target?.let {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(it, 13f))
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        drinkRecords.forEach { recordDrink ->
            val record = recordDrink.drinkRecord
            Marker(
                state = MarkerState(LatLng(record.latitude, record.longitude)),
                title = record.name,
                snippet = "Rating: ${record.rating}"
            )
        }
    }
}