package thevoid.iam.ankoobservablecomponents.util

import iam.thevoid.e.remove
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import thevoid.iam.ankoobservablecomponents.data.Storage
import thevoid.iam.ankoobservablecomponents.data.api.XeApi

const val SHORT_NAME_DIV = "h1.H2wTR"
const val LONG_NAME_DIV = "div.currencyHeader"

fun codeToValue(code: String) =
    Storage.single(code).onErrorReturn { "" }.flatMap { name ->
        if (name.isNotBlank()) Single.just(name) else
            XeApi.service.currencyInfo(code)
                .subscribeOn(Schedulers.io())
                .map { Jsoup.parse(it) }
                .map { doc ->
                    doc.select(SHORT_NAME_DIV).text().let { short ->
                        short.remove("${code.toUpperCase()} - ").let {
                            if (it != short) it else doc.select(LONG_NAME_DIV).text()
                        }
                    }
                }.doOnSuccess { Storage.put(code, it) }
    }
