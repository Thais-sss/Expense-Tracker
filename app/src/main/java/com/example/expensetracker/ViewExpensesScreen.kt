package com.example.expensetracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewExpensesScreen(
    expenses: List<Expense>,
    navigateToAddExpense: () -> Unit,
    onDeleteClick: (Expense) -> Unit // Add this parameter for delete action
) {
    var totalExpense by remember { mutableDoubleStateOf(0.0) }

    totalExpense = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }
        .sumByDouble { it.amount.toDouble() }

    val validExpenses = expenses.filter { it.name.isNotBlank() && it.amount.isNotBlank() }

    MaterialTheme(
        colors = darkColors(
            primary = Color(0xFF03DAC6),
            onPrimary = Color.Black
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            item {
                Button(
                    onClick = { navigateToAddExpense() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(0xFF03DAC6), shape = RoundedCornerShape(10.dp))
                ) {
                    Text("Add Expense")
                }
            }

            if (validExpenses.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No expenses added yet", modifier = Modifier.padding(16.dp), color = Color.White)
                    }
                }
            } else {
                items(validExpenses) { expense ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                Color(0xFF03DAC6),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .animateContentSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.cartt),
                                contentDescription = "Expense Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Name: ${expense.name}", modifier = Modifier.padding(8.dp))
                        }
                        Text("Amount: ${expense.amount}", modifier = Modifier.padding(8.dp))
                        // Add delete icon with click listener
                        Image(
                            painter = painterResource(id = R.drawable.dele), // Replace with your custom image
                            contentDescription = "Delete Expense",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(8.dp)
                                .clickable { onDeleteClick(expense) } // Add click listener
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(
                                Color(0xFF03DAC6),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .animateContentSize()
                    ) {
                        Text("Total Expense: $totalExpense", modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ViewExpensesScreenPreview() {
    // Sample list of expenses for preview
    val sampleExpenses = listOf(
        Expense("Groceries", "50.0"),
        Expense("Utilities", "100.0"),
        Expense("Dinner", "30.0")
    )
    // Function to navigate to add expense (not used in preview)
    val navigateToAddExpense: () -> Unit = {}
    // Function to handle delete action (not used in preview)
    val onDeleteClick: (Expense) -> Unit = { /* Handle delete action */ }
    // Previewing the ViewExpensesScreen with sample data
    ViewExpensesScreen(expenses = sampleExpenses, navigateToAddExpense = navigateToAddExpense, onDeleteClick = onDeleteClick)
}

