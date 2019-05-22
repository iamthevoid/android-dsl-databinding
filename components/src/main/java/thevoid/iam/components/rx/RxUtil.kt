package thevoid.iam.components.rx

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.toFlowableLatest() = toFlowable(BackpressureStrategy.LATEST)
fun <T> Observable<T>.toFlowableBuffer() = toFlowable(BackpressureStrategy.BUFFER)
fun <T> Observable<T>.toFlowableDrop() = toFlowable(BackpressureStrategy.DROP)
fun <T> Observable<T>.toFlowableError() = toFlowable(BackpressureStrategy.ERROR)
fun <T> Observable<T>.toFlowableMissing() = toFlowable(BackpressureStrategy.MISSING)

fun <T> Single<T>.toFlowableLatest() = toObservable().toFlowableLatest()
fun <T> Single<T>.toFlowableBuffer() = toObservable().toFlowableBuffer()
fun <T> Single<T>.toFlowableDrop() = toObservable().toFlowableDrop()
fun <T> Single<T>.toFlowableError() = toObservable().toFlowableError()
fun <T> Single<T>.toFlowableMissing() = toObservable().toFlowableMissing()

