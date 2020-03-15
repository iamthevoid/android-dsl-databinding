package iam.thevoid.noxml.rx2.data

import io.reactivex.Single
import io.reactivex.processors.PublishProcessor

class RxRefreshSingle<T>(requestSupplier: () -> Single<T>) {

    private val refresh by lazy { PublishProcessor.create<Any>().toSerialized() }

    val request by lazy {
        requestSupplier()
            .repeatWhen { it.flatMapMaybe { refresh.firstElement() } }
    }

    fun refresh() = refresh.onNext(Any())

}