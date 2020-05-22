package iam.thevoid.noxml.demo.util

import iam.thevoid.e.remove
import iam.thevoid.e.safe
import org.jsoup.Jsoup
import iam.thevoid.noxml.demo.data.Storage
import iam.thevoid.noxml.demo.data.api.XeApi

const val SHORT_NAME_DIV = "h1.H2wTR"
const val LONG_NAME_DIV = "div.currencyHeader"

suspend fun codeToValue(code: String) : String =
    Storage.get(code)?.takeIf { it.isNotBlank() } ?: XeApi.service.currencyInfo(code)
        .let { try { Jsoup.parse(it) } catch (e : Exception) {
            e.printStackTrace()
            null
        } }?.let { doc ->
        doc.select(SHORT_NAME_DIV).text().let { short ->
            short.remove("${code.toUpperCase()} - ").let {
                if (it != short) it else doc.select(LONG_NAME_DIV).text()
            }
        }.also { Storage.put(code, it) }
    }.safe()