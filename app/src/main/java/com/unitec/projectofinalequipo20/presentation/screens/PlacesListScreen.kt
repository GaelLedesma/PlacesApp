package com.unitec.projectofinalequipo20.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unitec.projectofinalequipo20.firebase.PlacesFirebaseRepository
import com.unitec.projectofinalequipo20.data.models.Place
import kotlinx.coroutines.launch

@Composable
fun PlacesListScreen(
    onAddPlace: () -> Unit,
    onPlaceSelected: (String) -> Unit
) {
    val repo = remember { PlacesFirebaseRepository() }
    val scope = rememberCoroutineScope()

    var places by remember { mutableStateOf<List<Place>>(emptyList()) }

    LaunchedEffect(true) {
        scope.launch {
            places = repo.getPlaces()
        }
    }

    Column(Modifier.padding(20.dp)) {

        Text("Lugares Registrados", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onAddPlace,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Lugar")
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn {
            items(places.size) { index ->
                val place = places[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onPlaceSelected(place.id) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(place.name, style = MaterialTheme.typography.titleLarge)
                        Text(place.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}