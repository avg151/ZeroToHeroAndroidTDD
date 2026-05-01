package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
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
                    var state by rememberSaveable { mutableStateOf<ScreenState>(ScreenState.Initial) }
                    Box(contentAlignment = Alignment.Center) {
                        when (state) {
                            ScreenState.Initial -> Button(
                                onClick = { state = ScreenState.Loading },
                                modifier = Modifier.testTag("loadButton")
                            ) {
                                Text("Load")
                            }

                            ScreenState.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.testTag("progress"))
                                LaunchedEffect(Unit) {
                                    delay(1000)
                                    state = ScreenState.Success
                                }
                            }

                            ScreenState.Success -> Text(
                                text = "Success!",
                                modifier = Modifier.testTag("result")
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface ScreenState : Parcelable {
    @Parcelize
    data object Initial : ScreenState

    @Parcelize
    data object Loading : ScreenState

    @Parcelize
    data object Success : ScreenState
}




