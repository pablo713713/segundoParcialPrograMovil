package com.calyrsoft.ucbp1.features.notification.data.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FirebaseService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Si el mensaje contiene datos (payload tipo data)
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        // Si el mensaje contiene una notificación
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    // (Opcional) Si quieres registrar el nuevo token cuando se renueve
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // Aquí podrías enviarlo a tu servidor o guardarlo en DataStore
    }
}
