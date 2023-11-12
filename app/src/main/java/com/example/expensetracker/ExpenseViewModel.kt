package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDate

//data class Expense(val name: String, val amount: String)
data class Expense @RequiresApi(Build.VERSION_CODES.O) constructor(val name: String, val amount: String, val date: LocalDate = LocalDate.now())

//class YourViewModel : ViewModel() {
//    val currentScreen = mutableStateListOf(Screen.AddExpense)
//
//    fun navigateTo(screen: Screen) {
//        currentScreen.value = screen
//    }
//
//    var expenseList by mutableStateOf(mutableListOf<Expense>())
//
//    fun addExpense(newExpense: Expense) {
//        expenseList.add(newExpense)
//    }
//}