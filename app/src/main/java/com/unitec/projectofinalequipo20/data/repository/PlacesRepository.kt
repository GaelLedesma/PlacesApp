package com.unitec.projectofinalequipo20.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.unitec.projectofinalequipo20.data.models.Place
import kotlinx.coroutines.tasks.await

class PlacesRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("places")

    suspend fun addPlace(place: Place) {
        collection.add(place).await()
    }

    suspend fun getPlaces(): List<Place> {
        return collection.get().await().toObjects(Place::class.java)
    }

    suspend fun getPlaceById(id: String): Place? {
        val snapshot = collection.document(id).get().await()
        return snapshot.toObject(Place::class.java)
    }
}