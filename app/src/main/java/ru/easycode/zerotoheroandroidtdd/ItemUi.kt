package ru.easycode.zerotoheroandroidtdd

data class ItemUi(val id: Long, val text: String) {
    fun areItemsSame(other: ItemUi): Boolean = id == other.id
}
