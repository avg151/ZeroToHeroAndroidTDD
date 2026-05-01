package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuizScreen()
                }
            }
        }
    }
}

@Composable
fun QuizScreen() {
    var clickedCorrect by remember { mutableStateOf<Boolean?>(null) }

    Column {
        Text(text = "some question")

        val correctColor = when (clickedCorrect) {
            null -> Color.Yellow
            else -> Color.Green
        }
        Text(
            text = "correct",
            modifier = Modifier
                .background(correctColor)
                .clickable { clickedCorrect = true }
        )

        val incorrectColor = when (clickedCorrect) {
            null -> Color.Yellow
            true -> Color.Gray
            false -> Color.Red
        }
        Text(
            text = "incorrect",
            modifier = Modifier
                .background(incorrectColor)
                .clickable { clickedCorrect = false }
        )
    }
}