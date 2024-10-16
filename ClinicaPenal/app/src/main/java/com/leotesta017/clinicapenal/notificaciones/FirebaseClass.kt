package com.leotesta017.clinicapenal.notificaciones

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.leotesta017.clinicapenal.R
import com.leotesta017.clinicapenal.view.MainActivity

class FirebaseClass : FirebaseMessagingService() {

    companion object {
        internal const val TAG = "FirebaseClass"
        private const val CHANNEL_ID = "DefaultChannel"
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "FCM Token: $token")
        // Llamar a una función para guardar el token en Firebase Firestore
        saveTokenToDatabase(token)
    }

    private fun saveTokenToDatabase(token: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        if (userId != null) {
            val userRef = db.collection("usuarios").document(userId)
            userRef.update("fcmTokens", FieldValue.arrayUnion(token))
                .addOnSuccessListener {
                    Log.d(TAG, "Token successfully updated in Firestore")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error updating token", e)
                }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Verifica si el mensaje contiene una carga de datos
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleNow() // Manejar la carga de datos aquí si es necesario
        }

        // Verifica si el mensaje contiene una notificación
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            it.body?.let { body ->
                val title = remoteMessage.notification?.title ?: "Nuevo Mensaje"
                val groupKey = "com.leotesta017.clinicapenal.MESSAGE_GROUP" // Clave del grupo de notificaciones
                val groupId = 1 // ID único del grupo para la notificación resumen

                // Crea la notificación individual para este mensaje
                createNotification(title, body, groupKey, groupId)

                // Actualiza la notificación resumen (para agrupar los mensajes)
                createSummaryNotification(groupKey, groupId, title, body)
            }
        }
    }

    private fun createNotification(title: String, message: String, groupKey: String, groupId: Int) {
        val notificationId = System.currentTimeMillis().toInt() // Un ID único para cada notificación
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logoapp) // Cambia el ícono si lo necesitas
            .setContentTitle(title)
            .setContentText(message)
            .setGroup(groupKey) // Añadir a un grupo
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification) // Muestra la notificación individual
    }

    private fun createSummaryNotification(groupKey: String, groupId: Int, title: String, message: String) {
        val summaryNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logoapp) // Cambia el ícono si lo necesitas
            .setContentTitle("Tienes nuevos mensajes")
            .setContentText("Tienes varios mensajes nuevos.")
            .setStyle(
                NotificationCompat.InboxStyle() // Estilo de bandeja de entrada
                    .addLine("$title: $message") // Añadir el título y mensaje al resumen
            )
            .setGroup(groupKey) // El mismo groupKey para agrupar
            .setGroupSummary(true) // Este es el resumen del grupo
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(groupId, summaryNotification) // Muestra la notificación resumen
    }

    private fun handleNow() {
        Log.d(TAG, "Handling data payload.")
        // Implementa el manejo de cargas útiles si es necesario
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logoapp)
            .setContentTitle("Notificación")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Notificaciones generales", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}

