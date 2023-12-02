package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpenseScreen(viewModel: YourViewModel = YourViewModel.instance) {
    Column(
        // elements of column being customized
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            // text fields
            text = "Expense Tracker App",
            modifier = Modifier.padding(16.dp)
        )

        // Add the following composable to display expenses
        ViewExpensesScreen(
            expenses = viewModel.expenses,
            onDeleteClick = { expense ->
                // Handle the delete action here
                viewModel.deleteExpense(expense)
            },
            navigateToAddExpense = {
                // Handle navigation to AddExpenseScreen here
                viewModel.navigateTo(Screen.AddExpense)
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewExpenseScreen() {
    ExpenseScreen()
}