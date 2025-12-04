package com.unitec.projectofinalequipo20.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.unitec.projectofinalequipo20.data.models.Place
import kotlinx.coroutines.tasks.await

class PlacesFirebaseRepository {

    private val db = Firebase.firestore
    private val placesCollection = db.collection("places")

    suspend fun addPlace(place: Place) {
        val docRef = placesCollection.add(
            mapOf(
                "name" to place.name,
                "description" to place.description,
                "lat" to place.lat,
                "lng" to place.lng
            )
        ).await()

        docRef.update("id", docRef.id).await()
    }

    suspend fun getPlaces(): List<Place> {
        val snapshot = placesCollection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            val name = doc.getString("name") ?: return@mapNotNull null
            val desc = doc.getString("description") ?: ""
            val lat = doc.getDouble("lat") ?: return@mapNotNull null
            val lng = doc.getDouble("lng") ?: return@mapNotNull null

            Place(
                id = doc.id,
                name = name,
                description = desc,
                lat = lat,
                lng = lng
            )
        }
    }

    suspend fun getPlaceById(id: String): Place? {
        val doc = placesCollection.document(id).get().await()
        if (!doc.exists()) return null

        val name = doc.getString("name") ?: return null
        val desc = doc.getString("description") ?: ""
        val lat = doc.getDouble("lat") ?: return null
        val lng = doc.getDouble("lng") ?: return null

        return Place(
            id = doc.id,
            name = name,
            description = desc,
            lat = lat,
            lng = lng
        )
    }

    suspend fun deletePlace(id: String) {
        placesCollection.document(id).delete().await()
    }
    suspend fun updatePlace(id: String, name: String, description: String) {
        val updates = mapOf(
            "name" to name,
            "description" to description
        )

        placesCollection.document(id).update(updates).await()
    }
}