// package name
package com.example.expensetracker

// import android and compose libraries
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

// it requires a minimum api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
// it takes viewModel as a parameter
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

// composable function that helps previewing the UI in android studio
// very useful tool
// it saved me a lot of time so i dint have to be running the app
// every time i changed something in the ui aspect
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewExpenseScreen() {
    ExpenseScreen()
}