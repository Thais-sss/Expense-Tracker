// declaring the package name
package com.example.expensetracker

//importing important libraries from Kotlin and Android
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient (
    private val context: Context, //parameter one
    private val client: FusedLocationProviderClient //parameter two
): LocationClient { // the DefaultLocationClient class is implementing the LocationClient class
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> { // it will override the getLocationUpdates function
        return callbackFlow {// a callback is used for asynchronous operations
            if(!context.hasLocationPermission()) { // checking if the app has permission to track location
                throw LocationClient.LocationException("Missing location permission") // if it does not have permission, it will throw an error
            }

            // getting location service from the system
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            // check if GPS and network are enabled or not
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isGpsEnabled && !isNetworkEnabled) { // checking if Gps and network are both enabled
                throw LocationClient.LocationException("GPS is disabled") // if not, throws and exception
            }

            // here the interval is being specified and
            // being passed
            val request = LocationRequest.create()
                .setInterval(interval)
                .setFastestInterval(interval)

            // this gets called when the location changes
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location ->
                        launch { send(location) } // it will use the send function
                        // and send the last location
                    }
                }
            }

            // it requests location updates
            // location call back
            client.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )

            // when the flow is closed,
            // await close is called
            awaitClose {
                // it removes location updates
                client.removeLocationUpdates(locationCallback)
            }
        }
    }
}