package com.example.expensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(onAddExpensesClick: () -> Unit, onViewExpensesClick: () -> Unit) {
    MaterialTheme {
        Box(
            // applying some design
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Column(
                // applying some design
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    // adding image to welcome screen --- don't think I will keep it
                    painter = painterResource(id = R.drawable.dw),
                    contentDescription = "Digital Wallet"
                )

                // i am not entirely happy with my design at the moment
                // so i will probably try and enhance it in the future milestones
                Text("Welcome to Expense Tracker", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    // setting what to do if user clicks on this button
                    onClick = { onAddExpensesClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                ) {
                    Text("Add Expenses", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    // setting what to do if user clicks on this button
                    onClick = { onViewExpensesClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
                ) {
                    Text("View Expenses", color = Color.White)
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen({}, {})
}
