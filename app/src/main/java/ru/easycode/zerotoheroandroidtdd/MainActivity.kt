package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import ru.easycode.zerotoheroandroidtdd.ui.theme.ZeroToHeroAndroidTDDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ProductsViewModel(
            savedStateHandle = SavedStateHandle(),
            repository = ProductsRepositoryImpl(),
            runAsync = BaseRunAsync()
        )
        setContent {
            ZeroToHeroAndroidTDDTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf(Screen.PRODUCTS) }

                    when (currentScreen) {
                        Screen.PRODUCTS -> ProductsScreen(
                            viewModel = viewModel,
                            onOpenOrder = { currentScreen = Screen.ORDER_SETTINGS },
                            onOpenFilters = { currentScreen = Screen.FILTER_SETTINGS }
                        )

                        Screen.ORDER_SETTINGS -> OrderSettingsScreen(
                            viewModel = viewModel,
                            onClose = { currentScreen = Screen.PRODUCTS }
                        )

                        Screen.FILTER_SETTINGS -> FilterSettingsScreen(
                            viewModel = viewModel,
                            onClose = { currentScreen = Screen.PRODUCTS }
                        )
                    }
                }
            }
        }
    }
}

enum class Screen {
    PRODUCTS, ORDER_SETTINGS, FILTER_SETTINGS
}

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onOpenOrder: () -> Unit,
    onOpenFilters: () -> Unit
) {
    val products by viewModel.productsUiListStateFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Button(onClick = onOpenOrder, modifier = Modifier.testTag("order button")) {
                Text("order")
            }
            Button(onClick = onOpenFilters, modifier = Modifier.testTag("filters button")) {
                Text("filters")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("ProductsLazyColumn")
        ) {
            itemsIndexed(products) { index, item ->
                when (item) {
                    is ProductListUi.Base -> {
                        Column(modifier = Modifier.testTag("Product at $index")) {
                            Text(
                                text = item.name,
                                modifier = Modifier.testTag("Product name at $index")
                            )
                            Text(
                                text = item.os,
                                modifier = Modifier.testTag("Product os at $index")
                            )
                            Text(
                                text = item.ram.toString(),
                                modifier = Modifier.testTag("Product ram at $index")
                            )
                            Text(
                                text = item.price,
                                modifier = Modifier.testTag("Product price at $index")
                            )
                        }
                    }

                    is ProductListUi.Empty -> {
                        Text(text = "Nothing found", modifier = Modifier.testTag("nothing found"))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSettingsScreen(
    viewModel: ProductsViewModel,
    onClose: () -> Unit
) {
    val orders by viewModel.ordersUiListStateFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        orders.forEach { order ->
            Text(
                text = order.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("Order option ${order.name}")
                    .selectable(
                        selected = order.chosen,
                        onClick = {
                            viewModel.chooseOrder(order.name)
                            onClose()
                        }
                    )
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun FilterSettingsScreen(
    viewModel: ProductsViewModel,
    onClose: () -> Unit
) {
    val filters by viewModel.filtersUiListStateFlow.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        filters.forEach { filter ->
            Text(
                text = "${filter.category}: ${filter.value}",
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("filter ${filter.category} ${filter.value}")
                    .selectable(
                        selected = filter.chosen,
                        onClick = {
                            if (filter.chosen) {
                                viewModel.unchooseFilter(filter.id)
                            } else {
                                viewModel.chooseFilter(filter.id)
                            }
                        }
                    )
                    .padding(16.dp)
            )
        }
        Button(onClick = onClose, modifier = Modifier.testTag("save button")) {
            Text("save")
        }
    }
}
