package com.example.expensetracker

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseScreen(viewModel: YourViewModel, navigateToViewExpenses: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(), // setting size for the column
        horizontalAlignment = Alignment.CenterHorizontally // aligning column
    ) {
        Text( // setting text to be displayed
            text = "Add Expense",
            modifier = Modifier.padding(16.dp)
        )

        // declaring some variables
        val expenseNameState = remember { mutableStateOf("")}
        // specifying that their value will change or at least can change
        val amountState = remember { mutableStateOf("")}

        // text fields for expense details
        TextField(
            // saving the value
            value = expenseNameState.value, // Use state to manage input
            onValueChange = { expenseNameState.value = it },
            // label for user
            label = { Text("Expense Name") },
            // jetpack tools to deal with input
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )

        TextField(
            // same as above for amount now
            value = amountState.value, // Use state to manage input
            onValueChange = { amountState.value = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )

        // validate user input
        var showError by remember { mutableStateOf(false) }

        // button to add the expense
        // submit Button
        Button(
            onClick = {
                // Save the entered expense to a list, database, or ViewModel
                val newExpense = Expense(expenseNameState.value, amountState.value)

                // saving the values name and amount entered
                val name = expenseNameState.value
                val amount = amountState.value

                // if statement to validate
                if (name.isNotBlank() && amount.isNotBlank()) {
                    // checking if the fields are not blank
                    val newExpense = Expense(name, amount)
                    // if not blank, it will navigate user to the next screen
                    viewModel.addExpense(newExpense)
                    navigateToViewExpenses()

                    // Clear the text fields after submission
                    expenseNameState.value = ""
                    amountState.value = ""
                    // if the fields are blank, throw error
                } else {
                    showError = true
                }
            },
            // tools for design
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("Submit", color = Color.White)
        }
        if (showError) {
            Text("Please enter both name and amount", color = Color.Red)
        }
    }
}

@Composable
fun ViewExpenses(expenses: List<Expense>) {
    // Display the list of expenses
    LazyColumn {
        // rendering items of the list
        items(expenses) { expense ->
            // printing them
            Text("Name: ${expense.name}, Amount: ${expense.amount}")
        }
    }
}

