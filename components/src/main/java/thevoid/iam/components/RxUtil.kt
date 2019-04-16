package thevoid.iam.components

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

fun <T> Observable<T>.toFlowableLatest() = toFlowable(BackpressureStrategy.LATEST)