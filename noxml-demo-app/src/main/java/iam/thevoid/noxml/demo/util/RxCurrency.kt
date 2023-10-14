package iam.thevoid.noxml.demo.util

import iam.thevoid.noxml.demo.data.Storage
import iam.thevoid.noxml.demo.data.api.CoinbaseApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun codeToValue(code: String): Single<String> =
    Storage.get(code)?.takeIf { it.isNotBlank() }?.let { Single.just(it) }
        ?: CoinbaseApi.currencyInfoShared().subscribeOn(Schedulers.io())
            .firstOrError()
            .map { response -> response.data.find { it.id == code }!!.name }
            .doOnSuccess { Storage.put(code, it) }
