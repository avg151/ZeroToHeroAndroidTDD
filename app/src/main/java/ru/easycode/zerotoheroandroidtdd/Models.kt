package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

data class Product(val id: Int, val name: String, val price: String, val os: String, val ram: Int)

data class ProductFilter(val id: Int, val category: String, val name: String)

sealed interface ProductListUi {
    data class Base(val id: Int, val name: String, val price: String, val os: String, val ram: Int) : ProductListUi
    object Empty : ProductListUi
}

data class OrderUi(val name: String, val chosen: Boolean)

data class FilterUi(val id: Int, val category: String, val value: String, val chosen: Boolean)

interface ProductsRepository {
    suspend fun products(): List<Product>
    suspend fun orderList(): List<String>
    suspend fun filters(): List<ProductFilter>
}

interface RunAsync {
    fun <T : Any> runFlowCollect(
        scope: CoroutineScope,
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    )
}
