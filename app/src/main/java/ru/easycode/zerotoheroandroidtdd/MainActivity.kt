package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ListViewModel by viewModels {
        (application as App)
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
                    MainScreen(
                        state = state,
                        onAdd = { viewModel.add(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    state: List<RecordEntity>,
    onAdd: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Column {
        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.testTag("textField")
            )
            Button(
                onClick = {
                    onAdd(text)
                    text = ""
                },
                modifier = Modifier.testTag("addButton")
            ) {
                Text("Add")
            }
        }
        LazyColumn(modifier = Modifier.testTag("ListLazyColumn")) {
            itemsIndexed(state) { index, item ->
                Text(
                    text = item.text,
                    modifier = Modifier.testTag("Element at $index")
                )
            }
        }
    }
}
