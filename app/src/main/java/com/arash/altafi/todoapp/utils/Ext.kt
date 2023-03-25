package com.arash.altafi.todoapp.utils

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


inline fun <reified T> NavController.getBackStackLiveData(key: String): MutableLiveData<T>? =
    this.currentBackStackEntry?.savedStateHandle?.getLiveData(key)

fun <T> NavController.setBackStackLiveData(
    key: String,
    value: T?,
    destinationId: Int? = null,
    doBack: Boolean = true,
    inclusive: Boolean = false
) {
    destinationId?.let {
        this.getBackStackEntry(it).savedStateHandle[key] = value
        if (doBack)
            popBackStack(it, inclusive)
    } ?: kotlin.run {
        this.previousBackStackEntry?.savedStateHandle?.set(key, value)
        if (doBack)
            popBackStack()
    }

}

inline fun <reified T> Fragment.getBackStackData(key: String, crossinline result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.apply {
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                remove<T>(key)
            }
        })
        getLiveData<T>(key).observeFreshly(viewLifecycleOwner) {
            result(it)
        }
    }
}

fun <T> NavController.setBackStackData(
    key: String,
    value: T?,
    destinationId: Int? = null,
    doBack: Boolean = true,
    inclusive: Boolean = false
) {
    destinationId?.let {
        this.getBackStackEntry(it).savedStateHandle[key] = value
        if (doBack)
            popBackStack(it, inclusive)
    } ?: kotlin.run {
        this.previousBackStackEntry?.savedStateHandle?.set(key, value)
        if (doBack)
            popBackStack()
    }
}

@AnyThread
inline fun <reified T> MutableLiveData<T>.postNext(map: (T) -> T) {
    postValue(map(verifyLiveDataNotEmpty()))
}

@MainThread
inline fun <reified T> MutableLiveData<T>.setNext(map: (T) -> T) {
    value = map(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> LiveData<T>.verifyLiveDataNotEmpty(): T {
    return value ?: throw NullPointerException(
        "MutableLiveData<${T::class.java}> not contain value."
    )
}

@MainThread
inline fun <T> LiveData<T>.observeFreshly(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}


fun <F> runAfter(
    delay: Long, total: Long, fn: (Long) -> F, fc: () -> F,
    unit: TimeUnit = TimeUnit.MILLISECONDS
): Disposable {
    return Flowable.interval(0, delay, unit)
        .observeOn(AndroidSchedulers.mainThread())
        .takeWhile { it != total }
        .doOnNext { fn(it) }
        .doOnComplete { fc() }
        .subscribe()
}

fun <F> runAfter(
    delay: Long, fx: () -> F, unit: TimeUnit = TimeUnit.MILLISECONDS
): Disposable {
    return Completable.timer(delay, unit)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete { fx() }
        .subscribe()
}

fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

fun <T> debounceCancelable(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T?) -> Unit {
    var debounceJob: Job? = null
    return { param: T? ->
        debounceJob?.cancel()
        if (param != null)
            debounceJob = scope.launch {
                delay(waitMs)
                destinationFunction(param)
            }
    }
}