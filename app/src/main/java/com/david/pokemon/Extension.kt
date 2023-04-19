package com.david.pokemon

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun ViewModel.runIoCoroutine(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(
        Dispatchers.IO +
                CoroutineExceptionHandler { _, exception ->
                    Log.i("viewModelCoroutine", "exception", exception)
                }
    ) {
        block()
    }
}