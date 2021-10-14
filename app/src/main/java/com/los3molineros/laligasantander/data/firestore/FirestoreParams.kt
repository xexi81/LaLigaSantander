package com.los3molineros.laligasantander.data.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.los3molineros.laligasantander.common.DateClass
import com.los3molineros.laligasantander.data.model.Params
import kotlinx.coroutines.tasks.await

class FirestoreParams() {
    private val db = Firebase.firestore

    suspend fun getParams(): Params? {
        val result = db.collection("params").document("param").get().await()

        return result.toObject(Params::class.java)
    }

    fun writeSeason(seasonId: Int) {
        val seasonMap = mapOf("lastSeasonCheck" to DateClass().getUTCDate(), "seasonId" to seasonId)
        db.collection("params").document("param").update(seasonMap)
    }
}