package com.muzz.androidexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.muzz.androidexercise.feature.chat.ui.ChatScreen
import com.muzz.androidexercise.ui.theme.AndroidExerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidExerciseTheme {
                ChatScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    AndroidExerciseTheme {
        ChatScreen()
    }
}
