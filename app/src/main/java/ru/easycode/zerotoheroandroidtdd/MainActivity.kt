package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val state by viewModel.stateFlow.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val s = state) {
            is ProgressUi.Disconnected -> {
                Text(
                    text = "No internet connection",
                    modifier = Modifier.testTag("noInternetConnection")
                )
                Button(
                    onClick = { },
                    enabled = false,
                    modifier = Modifier.testTag("loadButton")
                ) {
                    Text(text = "Load")
                }
            }

            is ProgressUi.Connected -> {
                Button(
                    onClick = {
                        viewModel.load()
                        viewModel.loadInternal()
                    },
                    modifier = Modifier.testTag("loadButton")
                ) {
                    Text(text = "Load")
                }
            }

            is ProgressUi.Loading -> {
                CircularProgressIndicator(modifier = Modifier.testTag("progress"))
            }

            is ProgressUi.Data -> {
                Text(text = s.value, modifier = Modifier.testTag("result"))
            }

            else -> {}
        }
    }
}
