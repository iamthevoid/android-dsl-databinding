package iam.thevoid.noxml.rx.rxdata

import io.reactivex.Completable
import io.reactivex.processors.PublishProcessor

class RxRefreshCompletable(requestSupplier: () -> Completable) {

    private val refresh by lazy { PublishProcessor.create<Any>().toSerialized() }

    val request by lazy {
        requestSupplier()
            .repeatWhen { it.flatMapMaybe { refresh.firstElement() } }
    }

    fun refresh() = refresh.onNext(Any())

}