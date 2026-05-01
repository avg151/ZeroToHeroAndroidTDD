package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface RunAsync {
    fun run(scope: CoroutineScope, background: suspend () -> Unit)

    class Base : RunAsync {
        override fun run(scope: CoroutineScope, background: suspend () -> Unit) {
            scope.launch(Dispatchers.IO) {
                background()
            }
        }
    }
}
