package iam.thevoid.noxml.demo.util

import iam.thevoid.e.remove
import iam.thevoid.noxml.demo.data.Storage
import iam.thevoid.noxml.demo.data.api.XeApi
import io.reactivex.Single
import org.jsoup.Jsoup

const val SHORT_NAME_DIV = "h1.H2wTR"
const val LONG_NAME_DIV = "div.currencyHeader"

fun codeToValue(code: String): Single<String> =
    Storage.get(code)?.takeIf { it.isNotBlank() }?.let { Single.just(it) }
        ?: XeApi.service.currencyInfo(code)
            .map { Jsoup.parse(it) }
            .map { doc ->
                doc.select(SHORT_NAME_DIV).text().let { short ->
                    short.remove("${code.toUpperCase()} - ").let {
                        if (it != short) it else doc.select(LONG_NAME_DIV).text()
                    }
                }
            }
            .doOnSuccess { Storage.put(code, it) }
//        .let { try { Jsoup.parse(it) } catch (e : Exception) {
//            e.printStackTrace()
//            null
//        } }?.let { doc ->
//        doc.select(SHORT_NAME_DIV).text().let { short ->
//            short.remove("${code.toUpperCase()} - ").let {
//                if (it != short) it else doc.select(LONG_NAME_DIV).text()
//            }
//        }.also { Storage.put(code, it) }
//    }.safe()