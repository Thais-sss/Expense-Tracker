package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.rememberNavController

class AddExpenseActivity : ComponentActivity() { // extends ComponentActivity
    private val viewModel: YourViewModel by viewModels() // creating view model

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) { // standard code
        super.onCreate(savedInstanceState)
        setContent { // setting content
            MaterialTheme {
                // storing the value of the currentScreen
                val currentScreen = viewModel.currentScreen.value
                // navController is used for navigation by calling the rememberNavController function
                val navController = rememberNavController()

                // checking what the current screen is
                when (currentScreen) { // a type of switch statement
                    is Screen.AddExpense -> { // if the currentScreen is addExpense, then display its view
                        AddExpenseScreen(viewModel = viewModel, navController = navController) { // passing viewModel as a parameter
                            viewModel.navigateTo(Screen.ViewExpenses) // navigating to another screen
                            finish() // finishing this current view
                        }
                    }
                    else -> { // if not, display viewExpenses
                        ViewExpenses(viewModel.expenses)
                    }
                }
            }
        }
    }
}
