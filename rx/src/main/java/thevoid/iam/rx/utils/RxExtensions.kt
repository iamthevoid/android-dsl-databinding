@file:Suppress("unused")

package thevoid.iam.rx.utils

import io.reactivex.*
import thevoid.iam.rx.rxdata.RxLoading

fun Completable.loading(loading: RxLoading) = compose(loading.completable())

fun <T> Single<T>.loading(loading: RxLoading) = compose(loading.single())

fun <T> Maybe<T>.loading(loading: RxLoading) = compose(loading.maybe())

fun <T> Observable<T>.loadingUntilComplete(loading: RxLoading) =
    compose(loading.observableUntilComplete())

fun <T> Observable<T>.loadingUntilNext(loading: RxLoading) = compose(loading.observableUntilNext())

fun <T> Flowable<T>.loadingUntilComplete(loading: RxLoading) =
    compose(loading.flowableUntilComplete())

fun <T> Flowable<T>.loadingUntilNext(loading: RxLoading) = compose(loading.flowableUntilNext())