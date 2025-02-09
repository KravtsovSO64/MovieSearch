package ru.yandex.practicum.moviessearch.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
delayMillis - задержка в миллисекундах;
coroutineScope - область видимости сопрограммы, в которой будут выполняться задачи;
useLastParam - флаг, указывающий, должна ли функция использовать последний переданный параметр;
action - функция, вызов которой мы хотим "задебаунсить".
 */


fun <T> debounce(delayMillis: Long,
                 coroutineScope: CoroutineScope,
                 useLastParam: Boolean,
                 action: (T) -> Unit): (T) ->Unit {
    var debounceJob: Job? = null
    return {param: T->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCancelled != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}