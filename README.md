# PlacesApp — Proyecto Final de Aplicaciones Móviles

## Universidad Tecnológica de México (UNITEC)

**Materia:** Aplicaciones Móviles con Jetpack Compose  
**Equipo:** 20  
**Integrantes:**  
- Gael Ledesma Caballero  
- Escudero Medina Eduar  
- Quiroz Sandoval Juan Pablo  

**Profesor:** Juan Luis Carrillo García  
**Fecha:** 3 de diciembre del 2025  

---

# Descripción General

PlacesApp es una aplicación móvil desarrollada en Kotlin utilizando Jetpack Compose, que permite registrar, visualizar y administrar lugares mediante un mapa interactivo integrado con Google Maps Compose y almacenamiento en Firebase Firestore.

Incluye geolocalización real del usuario, CRUD completo, navegación moderna y una interfaz creada con Material 3.

---

# Tecnologías Utilizadas

## Lenguaje y UI
- Kotlin  
- Jetpack Compose  
- Material Design 3  
- Navigation Compose  

## Funcionalidad
- Google Maps Compose  
- Geolocalización con FusedLocationProviderClient  
- Firebase Firestore  
- Kotlin Coroutines  

---

# Características Principales

## Mapa Interactivo  
Incluye:
- Marcadores de lugares guardados  
- Ubicación real del usuario  
- Zoom inicial dinámico  
- Interacción táctil (añadir lugares tocando el mapa)

Código de ejemplo:

```kotlin
GoogleMap(
    cameraPositionState = cameraState,
    onMapClick = { latLng ->
        onMapClick(latLng.latitude, latLng.longitude)
    }
) {
    Marker(
        state = MarkerState(
            position = LatLng(initialLat, initialLng)
        )
    )
}
```

---

## Registrar Lugares Tocando el Mapa  
Cuando el usuario toca el mapa en Home, se abre la pantalla AddPlace con latitud y longitud precargadas:

```kotlin
onMapClick = { lat, lng ->
    navController.navigate("add/$lat/$lng")
}
```

---

## CRUD Completo

### Crear  
Formulario para registrar un nuevo lugar con nombre, descripción y coordenadas.

### Listar  
Muestra todos los lugares guardados en Firestore.

### Detalle  
Incluye la información del lugar y un mapa del punto exacto.

### Editar  
Permite actualizar nombre y descripción.

### Eliminar  
Botón disponible en la pantalla de detalle.

---

# Geolocalización del Usuario

La app solicita permisos en tiempo real y obtiene la ubicación mediante:

```kotlin
val fused = LocationServices.getFusedLocationProviderClient(context)

fused.lastLocation.addOnSuccessListener { loc ->
    if (loc != null) {
        myLat = loc.latitude
        myLng = loc.longitude
    }
}
```

---

# Navegación

La app cuenta con 6 pantallas:

- Home  
- AddPlace  
- PlacesList  
- PlaceDetail  
- EditPlace  
- Settings  

Configuración con Navigation Compose:

```kotlin
NavHost(navController = nav, startDestination = "home") {
    composable("home") { HomeScreen(nav) }
    composable("add/{lat}/{lng}") { AddPlaceScreen(...) }
    composable("list") { PlacesListScreen(...) }
    composable("detail/{id}") { PlaceDetailScreen(id, nav) }
    composable("editPlace/{id}") { EditPlaceScreen(id, nav) }
}
```

---

# Estructura del Proyecto

```
/project
 ├── data
 │   ├── models
 │   │   └── Place.kt
 │   └── repository
 │       └── PlacesRepository.kt
 ├── firebase
 │   └── PlacesFirebaseRepository.kt
 ├── presentation
 │   ├── components
 │   │   └── MapComposable.kt
 │   └── screens
 │       ├── HomeScreen.kt
 │       ├── AddPlaceScreen.kt
 │       ├── PlacesListScreen.kt
 │       ├── PlaceDetailScreen.kt
 │       ├── EditPlaceScreen.kt
 │       └── SettingsScreen.kt
 ├── navigation
 │   └── AppNavigation.kt
 └── MainActivity.kt
```

---

# Instalación y Ejecución

## 1. Clonar repositorio

```bash
git clone https://github.com/usuario/PlacesApp.git
```

## 2. Abrir proyecto en Android Studio

Abrir la carpeta del proyecto y esperar a que Gradle sincronice.

## 3. Configurar Firebase

- Descargar el archivo `google-services.json`
- Colocarlo en `/app`
- Habilitar Firestore en la consola de Firebase

## 4. Configurar Google Maps

Agregar tu API Key en:

```
app/src/main/AndroidManifest.xml
```

Ejemplo:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_API_KEY" />
```

## 5. Ejecutar la app

```bash
Run > Run 'app'
```

---

# Generación del APK

Para generar el APK:

1. Build  
2. Build Bundle(s) / APK(s)  
3. Build APK(s)

El archivo se genera en:

```
app/build/outputs/apk/debug/app-debug.apk
```

---

# Explicación Técnica

El proyecto utiliza:

## Jetpack Compose
UI moderna declarativa sin XML.

## Gestión de estado
Basado en `remember` y `mutableStateOf`.

## Coroutines
Para operaciones asíncronas con Firestore:

```kotlin
scope.launch {
    repo.addPlace(place)
}
```

## Firebase Firestore
Almacenamiento en la nube en tiempo real.

## Google Maps Compose
Renderizado de mapa sin usar Fragments.
