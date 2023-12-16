// package name
package com.example.expensetracker

// necessary imports
import android.location.Location
import kotlinx.coroutines.flow.Flow

// defining an interface
interface LocationClient {
    // declaring function to get location update
    // this function is taking an interval
    fun getLocationUpdates(interval: Long): Flow<Location>

    // another class
    // this one will extend Exception class
    class LocationException(message: String): Exception()
}