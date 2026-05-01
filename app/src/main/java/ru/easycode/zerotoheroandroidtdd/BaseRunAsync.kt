package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BaseRunAsync : RunAsync {
    override fun <T : Any> runFlowCollect(
        scope: CoroutineScope,
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        scope.launch {
            flow.collect(collect)
        }
    }
}
