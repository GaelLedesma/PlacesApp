package com.unitec.projectofinalequipo20.presentation.components

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapComposable(
    markerList: List<Pair<Double, Double>>,
    navController: NavHostController,
    initialLat: Double?,
    initialLng: Double?,
    onMapClick: (Double, Double) -> Unit
) {

    val defaultPosition = LatLng(19.4326, -99.1332)

    val startPosition = LatLng(
        initialLat ?: defaultPosition.latitude,
        initialLng ?: defaultPosition.longitude
    )

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPosition, 15f)
    }

    LaunchedEffect(initialLat, initialLng) {
        if (initialLat != null && initialLng != null) {
            cameraState.position = CameraPosition.fromLatLngZoom(
                LatLng(initialLat, initialLng),
                15f
            )
        }
    }

    var tappedPosition by remember { mutableStateOf<LatLng?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            onMapClick = { latLng ->
                tappedPosition = latLng
                onMapClick(latLng.latitude, latLng.longitude)
            }
        ) {

            if (initialLat != null && initialLng != null) {
                Marker(
                    state = MarkerState(position = LatLng(initialLat, initialLng)),
                    title = "Tu ubicación"
                )
            }

            markerList.forEach { (lat, lng) ->
                Marker(
                    state = MarkerState(position = LatLng(lat, lng)),
                    title = "Lugar guardado"
                )
            }

            tappedPosition?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Nuevo lugar"
                )
            }
        }

        FloatingActionButton(
            onClick = {
                val target = if (initialLat != null && initialLng != null) {
                    LatLng(initialLat, initialLng)
                } else {
                    defaultPosition
                }

                cameraState.position = CameraPosition.fromLatLngZoom(target, 15f)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("📍")
        }

        FloatingActionButton(
            onClick = { navController.navigate("list") },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("📄")
        }
    }
}