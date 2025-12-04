package com.unitec.projectofinalequipo20

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.unitec.projectofinalequipo20.navigation.AppNavigation
import com.unitec.projectofinalequipo20.ui.theme.ProjectoFinalEquipo20Theme

class MainActivity : ComponentActivity() {

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d("PERMISSION", "Location granted: $granted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        setContent {
            ProjectoFinalEquipo20Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}