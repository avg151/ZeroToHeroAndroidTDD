package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ProgressViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = extras.createSavedStateHandle()
                return ProgressViewModel(
                    savedStateHandle = savedStateHandle,
                    repository = RepositoryImpl(),
                    runAsync = RunAsyncImpl()
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    Box(contentAlignment = Alignment.Center) {
                        when (val currentState = state) {
                            is ProgressUi.Initial -> Button(
                                onClick = {
                                    viewModel.load()
                                    viewModel.loadInternal()
                                },
                                modifier = Modifier.testTag("loadButton")
                            ) {
                                Text("Load")
                            }

                            is ProgressUi.Loading -> {
                                CircularProgressIndicator(modifier = Modifier.testTag("progress"))
                            }

                            is ProgressUi.Data -> Text(
                                text = currentState.value,
                                modifier = Modifier.testTag("result")
                            )
                        }
                    }
                }
            }
        }
    }
}

private class RepositoryImpl : Repository {
    override suspend fun load(): String {
        delay(1000)
        return "Success!"
    }
}

private class RunAsyncImpl : RunAsync {
    override fun <T : Any> runAsync(
        scope: CoroutineScope,
        background: suspend () -> T,
        ui: (T) -> Unit
    ) {
        scope.launch {
            val result = background()
            ui(result)
        }
    }
}
