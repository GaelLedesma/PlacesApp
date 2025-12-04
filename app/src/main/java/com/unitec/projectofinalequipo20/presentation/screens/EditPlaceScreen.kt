package com.unitec.projectofinalequipo20.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.unitec.projectofinalequipo20.firebase.PlacesFirebaseRepository
import kotlinx.coroutines.launch

@Composable
fun EditPlaceScreen(id: String, navController: NavHostController) {

    val repo = PlacesFirebaseRepository()
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Cargar datos actuales
    LaunchedEffect(id) {
        val place = repo.getPlaceById(id)
        place?.let {
            name = it.name
            description = it.description!!
        }
    }

    Column(Modifier.padding(20.dp)) {

        Text("Editar Lugar", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    repo.updatePlace(id, name, description)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }
    }
}