package com.example.seesort.ui.util

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun customTextButton(buttonText: String, onclick: () -> Unit) {
    ElevatedButton(onClick = onclick) {
        Text(text = buttonText)
    }
}
