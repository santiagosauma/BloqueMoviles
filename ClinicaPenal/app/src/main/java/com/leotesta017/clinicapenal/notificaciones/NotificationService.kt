package com.leotesta017.clinicapenal.notificaciones

import android.content.Context
import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeUnit

class NotificationService(private val context: Context) {
    private companion object {
        const val FCM_API = "https://fcm.googleapis.com/v1/projects/YOUR_PROJECT_ID/messages:send"
        const val SCOPE = "https://www.googleapis.com/auth/firebase.messaging"
        const val TAG = "NotificationService"
    }

    private var credentials: GoogleCredentials? = null
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    data class Message(val message: FCMMessage)
    data class FCMMessage(val token: String, val notification: NotificationData)
    data class NotificationData(val title: String, val body: String)

    private fun getAccessToken(): String {
        if (credentials == null) {
            try {
                context.assets.open("service-account.json").use { inputStream ->
                    credentials = GoogleCredentials
                        .fromStream(inputStream)
                        .createScoped(listOf(SCOPE))
                }
            } catch (e: IOException) {
                Log.e(TAG, "Error reading service account file", e)
                throw IllegalStateException("Failed to read service account file", e)
            }
        }

        return try {
            credentials?.refreshIfExpired()
            credentials?.accessToken?.tokenValue ?: throw IllegalStateException("Failed to obtain access token")
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing credentials", e)
            throw IllegalStateException("Failed to refresh credentials", e)
        }
    }

    suspend fun sendNotification(token: String, title: String, message: String, userId: String) {
        withContext(Dispatchers.IO) {
            try {
                val fcmMessage = Message(
                    FCMMessage(
                        token = token,
                        notification = NotificationData(title = title, body = message)
                    )
                )

                val jsonBody = Gson().toJson(fcmMessage)
                val accessToken = getAccessToken()

                val request = Request.Builder()
                    .url(FCM_API)
                    .addHeader("Authorization", "Bearer $accessToken")
                    .addHeader("Content-Type", "application/json")
                    .post(jsonBody.toRequestBody("application/json".toMediaType()))
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        val errorBody = response.body?.string()
                        Log.e(TAG, "FCM API error: $errorBody")

                        if (errorBody?.contains("NotRegistered") == true || errorBody?.contains("InvalidRegistration") ?: false) {
                            removeInvalidToken(userId, token)
                        }

                        throw Exception("Failed to send notification: $errorBody")
                    }
                    Log.d(TAG, "Notification sent successfully to token: $token")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error sending notification", e)
            }
        }
    }

    private fun removeInvalidToken(userId: String, token: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("usuarios").document(userId)
            .update("fcmTokens", FieldValue.arrayRemove(token))
            .addOnSuccessListener {
                Log.d(TAG, "Token removed successfully for user $userId")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error removing token for user $userId", e)
            }
    }
    val db = FirebaseFirestore.getInstance()

    suspend fun getTokensForAbogadosYEstudiantes(): List<String> {
        val tokenList = mutableListOf<String>()

        try {
            val abogadosSnapshot = db.collection("usuarios")
                .whereIn("tipo", listOf("abogado", "estudiante"))
                .get()
                .await() // Await if you're using Kotlin coroutines

            for (document in abogadosSnapshot.documents) {
                val fcmTokens = document.get("fcmTokens") as? List<String> // Asegúrate de que "fcmTokens" es una lista
                fcmTokens?.let {
                    tokenList.addAll(it) // Agrega todos los tokens a la lista
                }
            }

        } catch (e: Exception) {
            Log.e("NotificationService", "Error getting tokens: ", e)
        }

        return tokenList
    }

    suspend fun sendNotificationToAllAbogadosYEstudiantes(
        title: String,
        message: String
    ) {
        val tokens = getTokensForAbogadosYEstudiantes()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val notificationService = NotificationService(context) // Asegúrate de tener el contexto adecuado

        tokens.forEach { token ->
            notificationService.sendNotification(token, title, message, userId)
        }
    }

}
