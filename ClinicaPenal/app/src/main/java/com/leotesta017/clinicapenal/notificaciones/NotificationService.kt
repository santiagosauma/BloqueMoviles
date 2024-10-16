package com.leotesta017.clinicapenal.notificaciones

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.MainActivity
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
        const val FCM_API = "https://fcm.googleapis.com/v1/projects/bufetecapp/messages:send"
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
                .whereIn("tipo", listOf("colaborador", "estudiante"))
                .get()
                .await()

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

    suspend fun getTokenByUserId(userId: String): List<String>? {
        return try {
            // Obtiene el documento del usuario usando su ID
            val userSnapshot = db.collection("usuarios")
                .document(userId)
                .get()
                .await()

            // Verifica si el documento existe y obtiene los tokens
            if (userSnapshot.exists()) {
                val fcmTokens = userSnapshot.get("fcmTokens") as? List<String>
                fcmTokens
            } else {
                Log.e("NotificationService", "No se encontró al usuario con ID: $userId")
                null
            }
        } catch (e: Exception) {
            Log.e("NotificationService", "Error buscando tokens para el usuario con ID: $userId", e)
            null
        }
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

    suspend fun sendNotificationToAssignedSpecificUser(
        title: String,
        message: String,
        user_id: String
    ) {
        val tokens = getTokenByUserId(user_id)

        val notificationService = NotificationService(context)
        tokens?.forEach { token ->
            notificationService.sendNotification(token, title, message, user_id)
        }
    }

    suspend fun sendMessageNotificationToAssignedSpecificUser(
        title: String,
        message: String,
        user_id: String
    ) {
        // Obtener el ID del usuario que está enviando el mensaje
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        // Si el usuario que envía el mensaje es el mismo que el destinatario, no enviar notificación


        // Obtener tokens del usuario receptor
        val tokens = getTokenByUserId(user_id)
        val groupKey = "com.leotesta017.clinicapenal.MESSAGE_GROUP"
        val groupId = 1

        val notificationService = NotificationService(context)

        // Enviar notificaciones solo al receptor
        tokens?.forEach { token ->
            notificationService.sendNotificationGrouped(token, title, message, user_id, groupKey, groupId)
        }
    }


    suspend fun NotificationService.sendNotificationGrouped(
        token: String,
        title: String,
        message: String,
        userId: String, // Este userId es el del receptor
        groupKey: String,
        groupId: Int
    ) {

        withContext(Dispatchers.IO) {
            try {
                // Crear la notificación para el receptor
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

                        if (errorBody?.contains("NotRegistered") == true || errorBody?.contains("InvalidRegistration") == true) {
                            removeInvalidToken(userId, token)
                        }

                        throw Exception("Failed to send notification: $errorBody")
                    }
                    Log.d(TAG, "Notification sent successfully to token: $token")

                    // Generar notificación resumen para el receptor (no para el que envía)
                }
                //createSummaryNotification(groupKey, groupId, title, message)

            } catch (e: Exception) {
                Log.e(TAG, "Error sending notification", e)
            }
        }
    }


    fun createSummaryNotification(groupKey: String, groupId: Int, title: String, message: String) {
        val summaryNotification = NotificationCompat.Builder(context.applicationContext, MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.logoapp)
            .setContentTitle("Tienes nuevos mensajes")
            .setContentText("Tienes varios mensajes nuevos.")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("$title: $message") // Añade el título y mensaje al resumen
            )
            .setGroup(groupKey)
            .setGroupSummary(true) // Establecer como resumen del grupo
            .setAutoCancel(true)
            .build()

        val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(groupId, summaryNotification)
    }
}
