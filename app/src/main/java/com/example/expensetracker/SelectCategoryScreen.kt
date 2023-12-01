package com.example.expensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun SelectCategoryScreen(onCategorySelected: (String) -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ExpenseCategoryBox(
                icon = R.drawable.games,
                category = "Groceries",
                onCategorySelected = onCategorySelected
            )
            ExpenseCategoryBox(
                icon = R.drawable.savings,
                category = "Vet",
                onCategorySelected = onCategorySelected
            )
            ExpenseCategoryBox(
                icon = R.drawable.gym,
                category = "Gym",
                onCategorySelected = onCategorySelected
            )
            ExpenseCategoryBox(
                icon = R.drawable.holidays,
                category = "Holidays",
                onCategorySelected = onCategorySelected
            )
        }
    }
}

@Composable
fun ExpenseCategoryBox(icon: Int, category: String, onCategorySelected: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFF03DAC6), RoundedCornerShape(10.dp))
            .clickable {
                onCategorySelected(category)
            }
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = category,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = category,
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun SelectCategoryScreenPreview() {
    SelectCategoryScreen {}
}
