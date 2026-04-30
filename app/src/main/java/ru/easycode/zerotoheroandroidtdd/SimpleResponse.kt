package ru.easycode.zerotoheroandroidtdd

import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("text")
    val text: String
)
