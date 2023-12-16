// package name
package com.example.expensetracker

// essential imports
// without them the code will not run or function properly
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

// declaring class location app that will extend application class
class LocationApp: Application() {

    // override oncreate
    override fun onCreate() {
        super.onCreate()
        // checking android version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // creating a notification channel
            val channel = NotificationChannel(
                "location",
                "Location",
                // setting importance to low
                NotificationManager.IMPORTANCE_LOW
            )
            // getting notification manager service from the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // creating the channel
            notificationManager.createNotificationChannel(channel)
        }
    }
}