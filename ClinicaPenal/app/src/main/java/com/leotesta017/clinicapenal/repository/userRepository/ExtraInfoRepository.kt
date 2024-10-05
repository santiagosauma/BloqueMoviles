package com.leotesta017.clinicapenal.repository.userRepository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import kotlinx.coroutines.tasks.await

class ExtraInfoRepository {

    private val firestore = Firebase.firestore

    // Obtener información extra por su ID
    suspend fun getExtraInfoById(id: String): ExtraInfo? {
        return try {
            val document = firestore.collection("extraInfo")
                .document(id)
                .get()
                .await()

            if (document.exists()) {
                document.toObject(ExtraInfo::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Agregar nueva información extra y actualizar la lista de extraInfo del caso
    suspend fun addExtraInfoToCase(extraInfo: ExtraInfo, caseId: String): Boolean {
        return try {
            val extraInfoRef = firestore.collection("extraInfo").document(extraInfo.extraInfo_id)

            // Primero, agregar la información extra a la colección "extraInfo"
            extraInfoRef.set(extraInfo).await()

            // Luego, actualizar la lista de información extra del caso
            val caseRef = firestore.collection("cases").document(caseId)
            caseRef.update("listExtraInfo", FieldValue.arrayUnion(extraInfo.extraInfo_id)).await()

            true
        } catch (e: Exception) {
            false
        }
    }

    // Actualizar información extra existente
    suspend fun updateExtraInfo(id: String, extraInfoData: Map<String, Any>): Boolean {
        return try {
            firestore.collection("extraInfo")
                .document(id)
                .update(extraInfoData)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
