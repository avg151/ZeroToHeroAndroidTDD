package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class ProductsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: ProductsRepository,
    private val runAsync: RunAsync
) : ViewModel() {

    private val _productsUiListStateFlow = MutableStateFlow<List<ProductListUi>>(emptyList())
    val productsUiListStateFlow: StateFlow<List<ProductListUi>> = _productsUiListStateFlow.asStateFlow()

    private val _ordersUiListStateFlow = MutableStateFlow<List<OrderUi>>(emptyList())
    val ordersUiListStateFlow: StateFlow<List<OrderUi>> = _ordersUiListStateFlow.asStateFlow()

    private val _filtersUiListStateFlow = MutableStateFlow<List<FilterUi>>(emptyList())
    val filtersUiListStateFlow: StateFlow<List<FilterUi>> = _filtersUiListStateFlow.asStateFlow()

    private var allProducts: List<Product> = emptyList()
    private var allFilters: List<ProductFilter> = emptyList()

    init {
        runAsync.runFlowCollect(viewModelScope, flow {
            val products = repository.products()
            val orders = repository.orderList()
            val filters = repository.filters()
            emit(Triple(products, orders, filters))
        }) { triple ->
            @Suppress("UNCHECKED_CAST")
            val result = triple as Triple<List<Product>, List<String>, List<ProductFilter>>
            val (products, orders, filters) = result
            allProducts = products
            allFilters = filters

            _ordersUiListStateFlow.value = orders.mapIndexed { index, s ->
                OrderUi(name = s, chosen = index == 0)
            }
            _filtersUiListStateFlow.value = allFilters.map {
                FilterUi(id = it.id, category = it.category, value = it.name, chosen = false)
            }
            updateProducts()
        }
    }

    fun chooseOrder(name: String) {
        _ordersUiListStateFlow.value = _ordersUiListStateFlow.value.map {
            it.copy(chosen = it.name == name)
        }
        updateProducts()
    }

    fun chooseFilter(id: Int) {
        val filterToChoose = allFilters.find { it.id == id } ?: return
        _filtersUiListStateFlow.value = _filtersUiListStateFlow.value.map {
            if (it.category == filterToChoose.category) {
                it.copy(chosen = it.id == id)
            } else {
                it
            }
        }
        updateProducts()
    }

    fun unchooseFilter(id: Int) {
        _filtersUiListStateFlow.value = _filtersUiListStateFlow.value.map {
            if (it.id == id) it.copy(chosen = false) else it
        }
        updateProducts()
    }

    private fun updateProducts() {
        val activeFilters = _filtersUiListStateFlow.value.filter { it.chosen }
        var filteredProducts = allProducts

        activeFilters.forEach { filter ->
            filteredProducts = filteredProducts.filter { product ->
                when (filter.category) {
                    "os" -> product.os == filter.value
                    "RAM" -> product.ram.toString() == filter.value
                    else -> true
                }
            }
        }

        val chosenOrder = _ordersUiListStateFlow.value.find { it.chosen }?.name
        val sortedProducts = when (chosenOrder) {
            "alphabet" -> filteredProducts.sortedBy { it.name }
            "price: low to high" -> filteredProducts.sortedBy { it.price.stripPrice() }
            "price: high to low" -> filteredProducts.sortedByDescending { it.price.stripPrice() }
            else -> filteredProducts
        }

        if (sortedProducts.isEmpty()) {
            _productsUiListStateFlow.value = listOf(ProductListUi.Empty)
        } else {
            _productsUiListStateFlow.value = sortedProducts.map {
                ProductListUi.Base(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    os = it.os,
                    ram = it.ram
                )
            }
        }
    }

    private fun String.stripPrice(): Int {
        return this.filter { it.isDigit() }.toInt()
    }
}
