package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.ViewModel
import com.example.expensetracker.AddExpenseScreen
import com.example.expensetracker.YourViewModel

class AddExpenseActivity : ComponentActivity() {
    private val viewModel: YourViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val currentScreen = viewModel.currentScreen.value

                when (currentScreen) {
                    is Screen.AddExpense -> {
                        AddExpenseScreen(viewModel = viewModel) {
                            viewModel.navigateTo(Screen.ViewExpenses)
                            finish()
                        }
                    }
                    else -> {
                        ViewExpenses(viewModel.expenses)
                    }
                }
            }
        }
    }
}
