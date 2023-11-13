package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

data class Expense @RequiresApi(Build.VERSION_CODES.O) constructor( // declaring class named Expense
    val name: String,  // passing constructor
    val amount: String, // this will probably have to change to double in future
    val date: LocalDate = LocalDate.now())

// FUTURE CHANGES
// also, I am not fully sure if I will have the time to implement the calendar or not
// that might be implemented or that might not get implemented at all
// in order to implement the addition and retrieves proper amount spent
// the amount will probably need to change from type string to double
