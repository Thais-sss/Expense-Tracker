package com.example.expensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onAddExpensesClick: () -> Unit, onViewExpensesClick: () -> Unit) {
    MaterialTheme{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // Centralized and styled welcome text
            Text(
                text = "Welcome",
                color = Color(0xFF03DAC6),
                style = MaterialTheme.typography.h3, // Use h3 for a larger font
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cover),
                    contentDescription = "Digital Wallet",
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Ensure the Image takes the full width
                        .height(300.dp) // Adjust the height as needed
                        .clip(RoundedCornerShape(16.dp)) // Apply rounded corners
                )
                ButtonWithIcon(
                    onClick = { onAddExpensesClick() },
                    icon = "plus",
                    buttonText = "Add Expenses"
                )
                ButtonWithIcon(
                    onClick = { onViewExpensesClick() },
                    icon = "plus", // Replace with the actual eye icon resource ID
                    buttonText = "View Expenses"
                )
            }
        }
    }
}

@Composable
fun ButtonWithIcon(
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF03DAC6),
    icon: Any? = null,
    buttonText: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                if (it is Int) {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 12.dp)
                    )
                    //pacer(modifier = Modifier.width(8.dp))

                } else if (it is ImageVector) {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 12.dp)
                    )
                    //Spacer(modifier = Modifier.height(35.dp)) // Adjust the spacing here
                } else if (it is String && it == "plus") {
                    Text("+", color = Color.Black, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
            }
            Text(buttonText, color = Color.Black, fontSize = 22.sp)
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen({}, {})
}
