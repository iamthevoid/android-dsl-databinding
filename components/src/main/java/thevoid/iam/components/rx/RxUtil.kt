package thevoid.iam.components.rx

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toFlowableLatest() = toFlowable(BackpressureStrategy.LATEST)
fun <T> Observable<T>.toFlowableBuffer() = toFlowable(BackpressureStrategy.BUFFER)
fun <T> Observable<T>.toFlowableDrop() = toFlowable(BackpressureStrategy.DROP)
fun <T> Observable<T>.toFlowableError() = toFlowable(BackpressureStrategy.ERROR)
fun <T> Observable<T>.toFlowableMissing() = toFlowable(BackpressureStrategy.MISSING)