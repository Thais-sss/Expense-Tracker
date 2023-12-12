package com.example.expensetracker

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    private val _currentCityFlow = MutableStateFlow<String>("Unknown City")
    val currentCityFlow: StateFlow<String> = _currentCityFlow

    // Update the current city when the location changes
    private fun updateCurrentCity(city: String) {
        _currentCityFlow.value = city
    }
    companion object {

        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        private val _currentCityFlow = MutableStateFlow("Unknown City")

        val currentCityFlow: StateFlow<String>
            get() = _currentCityFlow

        fun updateCurrentCity(city: String) {
            _currentCityFlow.value = city
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize the location client and start tracking the location
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()

                var cityName = "Unknown City"  // Declare cityName outside the try-catch block

                try {
                    val geocoder = Geocoder(this)
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    cityName = addresses?.firstOrNull()?.locality ?: "Unknown City"
                } catch (ex: IOException) {
                    cityName = "Unknown City"
                }

                // Update the city name in LocationService
                LocationService.updateCurrentCity(cityName)

                val updateNotification = notification.setContentText(
                    "Location: ($lat, $long), City: $cityName"
                )
                Log.d("Thais KEY", "$lat, $long, City: $cityName")
                notificationManager.notify(1, updateNotification.build())
            }

            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Handle any additional start commands here if needed
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }


}
