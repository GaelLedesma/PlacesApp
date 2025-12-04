package com.unitec.projectofinalequipo20.presentation.screens

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.unitec.projectofinalequipo20.firebase.PlacesFirebaseRepository
import com.unitec.projectofinalequipo20.data.models.Place
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun AddPlaceScreen(
    lat: Double,
    lng: Double,
    onSaved: () -> Unit
) {
    val repo = PlacesFirebaseRepository()
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column(Modifier.padding(20.dp)) {

        Text("Añadir lugar", style = MaterialTheme.typography.headlineLarge)

        OutlinedTextField(name, { name = it }, label = { Text("Nombre") })
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(desc, { desc = it }, label = { Text("Descripción") })
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    repo.addPlace(
                        Place("", name, desc, lat, lng)
                    )
                    onSaved()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}