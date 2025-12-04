package com.unitec.projectofinalequipo20.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.LocationServices
import com.unitec.projectofinalequipo20.firebase.PlacesFirebaseRepository
import com.unitec.projectofinalequipo20.data.models.Place
import com.unitec.projectofinalequipo20.presentation.components.MapComposable
import kotlinx.coroutines.launch


@Composable
fun RequestLocationPermission(onGranted: () -> Unit) {

    val context = LocalContext.current
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    var granted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        granted = isGranted
    }

    LaunchedEffect(Unit) {
        launcher.launch(permission)
    }

    if (granted) {
        onGranted()
    } else {
        Text("Por favor permite el acceso a la ubicación.")
    }
}


@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(nav: NavHostController) {

    var myLat by remember { mutableStateOf<Double?>(null) }
    var myLng by remember { mutableStateOf<Double?>(null) }
    val context = LocalContext.current
    val fused = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    RequestLocationPermission {

        fused.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                myLat = loc.latitude
                myLng = loc.longitude
            }
        }
    }

    val repo = PlacesFirebaseRepository()
    var places by remember { mutableStateOf(emptyList<Place>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            places = repo.getPlaces()
        }
    }

    Box(Modifier.fillMaxSize()) {

        MapComposable(
            markerList = places.map { it.lat to it.lng },
            navController = nav,
            initialLat = myLat,
            initialLng = myLng,
            onMapClick = { lat, lng ->
                nav.navigate("add/$lat/$lng")
            }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FloatingActionButton(onClick = {
                val lat = myLat ?: 19.4326
                val lng = myLng ?: -99.1332

                nav.navigate("add/$lat/$lng")
            }) {
                Text("+")
            }

            FloatingActionButton(onClick = { nav.navigate("list") }) {
                Text("📍")
            }

            FloatingActionButton(onClick = { nav.navigate("settings") }) {
                Text("⚙️")
            }
        }
    }
}