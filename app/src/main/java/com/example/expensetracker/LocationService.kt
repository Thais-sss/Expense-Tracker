// package name
package com.example.expensetracker

// doing important and necessary imports
// whitout them the code will not run
// or it will throw errors and exceptions
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

// declaring location service that extends service
// which is an android class
class LocationService : Service() {

    // using necessary scope of coroutine
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    // initialize location client
    private lateinit var locationClient: LocationClient

    // set current city as mutable state
    // so that it gets updated everytime that value changes
    // initially set to unknown
    private val _currentCityFlow = MutableStateFlow<String>("Unknown City")
    val currentCityFlow: StateFlow<String> = _currentCityFlow

    // Update the current city when the location changes
    private fun updateCurrentCity(city: String) {
        _currentCityFlow.value = city
    }
    // defining a companion object
    companion object {
        // constant actions
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"

        // storing current city flow
        private val _currentCityFlow = MutableStateFlow("Unknown City")

        // getting current city flow
        val currentCityFlow: StateFlow<String>
            get() = _currentCityFlow

        // updating current city flow
        fun updateCurrentCity(city: String) {
            _currentCityFlow.value = city
        }
    }

    // override onBind
    override fun onBind(intent: Intent?): IBinder? {
        // return null
        return null
    }

    // override on create
    override fun onCreate() {
        super.onCreate()

        // Initialize the location client and start tracking the location
        locationClient = DefaultLocationClient( // using a default location
            applicationContext,
            // i had to import Fused Location Provider
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

        // this will keep track of ongoing location tracking
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        // this will get the notification manager service
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // it will get the current location
        locationClient
            // it will update it every 10 seconds
            .getLocationUpdates(10000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                // getting latitude
                val lat = location.latitude.toString()
                // getting longitude
                val long = location.longitude.toString()

                // declaring cityName outside the try-catch block
                var cityName = "Unknown City"

                try {
                    val geocoder = Geocoder(this)
                    // get the latitude and longitude
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    // check if that is null or not
                    // if it is not null, then display it
                    // if it is null, then display unknown
                    cityName = addresses?.firstOrNull()?.locality ?: "Unknown City"
                    // handle exception by using catch
                } catch (ex: IOException) {
                    cityName = "Unknown City"
                }

                // Update the city name in LocationService
                LocationService.updateCurrentCity(cityName)

                // update notification val
                val updateNotification = notification.setContentText(
                    "Location: ($lat, $long), City: $cityName"
                )
                // here i printed a log just to check
                // if my implementation was correct or not
                Log.d("Thais KEY", "$lat, $long, City: $cityName")
                // get notified
                notificationManager.notify(1, updateNotification.build())
            }

            // launches service scope
            .launchIn(serviceScope)

        // it starts the foreground service
        startForeground(1, notification.build())
    }

    // any additional start commands
    // will be handled here
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // it stops the service
    private fun stop() {
        // it removes the foreground
        stopForeground(true)
        stopSelf()
    }

    // the service is destroyed and it cancels the coroutine
    override fun onDestroy() {
        super.onDestroy()
        // cancel service scope
        serviceScope.cancel()
    }


}
