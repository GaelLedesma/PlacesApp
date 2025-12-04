package com.unitec.projectofinalequipo20.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Ajustes", style = MaterialTheme.typography.headlineLarge)

        Spacer(Modifier.height(20.dp))

        Text("• Versión: 1.0")
        Text("• Proyecto Final UNITEC")
        Text("• Integrantes: Gael Ledesma Caballero, Escudero Medina Eduar," +
                "Quiroz Sandoval Juan Pablo")
        Text("• Sistema de geolocalización con Firebase y Maps")
    }
}