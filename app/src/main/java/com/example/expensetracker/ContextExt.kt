package com.example.expensetracker

// necessary imports
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.Manifest

fun Context.hasLocationPermission(): Boolean { // implementing an extended function
    return ContextCompat.checkSelfPermission(
        // checking if the app has authorization to track user location
        this,
        // i had to add info in the manifest in order to implement this checking properly
        Manifest.permission.ACCESS_COARSE_LOCATION
        // it will return if the access to user location is granted or not
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                // getting permission
                Manifest.permission.ACCESS_FINE_LOCATION
                // for this to work, I had to modify the manifest as well
            ) == PackageManager.PERMISSION_GRANTED
}