package com.unitec.projectofinalequipo20.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unitec.projectofinalequipo20.presentation.screens.*

@Composable
fun AppNavigation(nav: NavHostController) {
    NavHost(navController = nav, startDestination = "home") {

        composable("home") {
            HomeScreen(nav)
        }

        composable("list") {
            PlacesListScreen(
                onAddPlace = {
                    // abrir AddPlace con coords por defecto
                    nav.navigate("add/0.0/0.0")
                },
                onPlaceSelected = { id ->
                    nav.navigate("detail/$id")
                }
            )
        }

        composable("add/{lat}/{lng}") { backStack ->
            val lat = backStack.arguments?.getString("lat")?.toDouble() ?: 0.0
            val lng = backStack.arguments?.getString("lng")?.toDouble() ?: 0.0

            AddPlaceScreen(
                lat = lat,
                lng = lng,
                onSaved = { nav.popBackStack() }
            )
        }

        composable("detail/{id}") { entry ->
            val id = entry.arguments?.getString("id") ?: ""

            PlaceDetailScreen(
                id = id,
                navController = nav
            )
        }

        composable("editPlace/{id}") { entry ->
            val id = entry.arguments?.getString("id") ?: ""
            EditPlaceScreen(
                id = id,
                navController = nav
            )
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}