package com.unitec.projectofinalequipo20.presentation.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

import com.unitec.projectofinalequipo20.firebase.PlacesFirebaseRepository
import com.unitec.projectofinalequipo20.data.models.Place
import kotlinx.coroutines.launch

@Composable
fun PlaceDetailScreen(id: String, navController: NavHostController) {

    val repo = PlacesFirebaseRepository()
    val scope = rememberCoroutineScope()

    var place by remember { mutableStateOf<Place?>(null) }

    // Cargar lugar desde Firestore
    LaunchedEffect(id) {
        place = repo.getPlaceById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Detalle del lugar", style = MaterialTheme.typography.headlineLarge)

        place?.let { it ->

            Spacer(Modifier.height(16.dp))
            Text("Nombre: ${it.name}")
            Text("Descripción: ${it.description}")
            Text("Lat: ${it.lat}")
            Text("Lng: ${it.lng}")

            Spacer(Modifier.height(16.dp))

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(
                        LatLng(it.lat, it.lng), 16f
                    )
                }
            ) {
                Marker(
                    state = MarkerState(position = LatLng(it.lat, it.lng)),
                    title = it.name
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    navController.navigate("editPlace/${it.id}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Editar")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    scope.launch {
                        repo.deletePlace(it.id)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Borrar")
            }
        }
    }
}