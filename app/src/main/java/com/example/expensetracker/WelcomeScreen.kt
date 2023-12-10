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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(onAddExpensesClick: () -> Unit, onViewExpensesClick: () -> Unit) {
    val shadowOffset = Offset(x= 8f, y=11f)
    val shadowColor = Color.White
    MaterialTheme{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            // Centralized and styled welcome text
            Text(
                text = "Welcome!",
                color = Color(0xFF03DAC6),
                style = TextStyle(
                    fontSize = 70.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center, // Center the text
                    shadow = Shadow(shadowColor, shadowOffset, 2f)// Use h3 for a larger font
                )
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
                Spacer(modifier = Modifier.height(45.dp)) // Add a vertical space between the image and the button
                ButtonWithIcon(
                    onClick = { onAddExpensesClick() },
                    icon = "add",
                    buttonText = "Add Expenses"
                )
                Spacer(modifier = Modifier.height(30.dp)) // Add a vertical space between the buttons
                ButtonWithIcon(
                    onClick = { onViewExpensesClick() },
                    icon = "eye", // Replace with the actual eye icon resource ID
                    buttonText = "View Expenses"
                )
                //Spacer(modifier = Modifier.height(5.dp)) // Add a vertical space between the buttons
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

                } else if (it is String && it == "add") {
                    // Replace "add" with the actual name of your image in the drawable folder
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 12.dp)
                    )
                    //Spacer(modifier = Modifier.height(35.dp)) // Adjust the spacing here
                } else if (it is String && it == "eye") {
                    // Replace "eye" with the actual name of your image in the drawable folder
                    Image(
                        painter = painterResource(id = R.drawable.eye),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 12.dp)
                    )
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
