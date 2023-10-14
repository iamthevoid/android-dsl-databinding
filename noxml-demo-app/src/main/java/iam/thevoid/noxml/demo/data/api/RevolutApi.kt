package iam.thevoid.noxml.demo.data.api

import iam.thevoid.noxml.demo.BuildConfig
import iam.thevoid.noxml.demo.data.api.model.CurrencyRate
import iam.thevoid.noxml.demo.data.api.model.RatesResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*
import kotlin.random.Random

object RevolutApi : Api<RevolutService>() {

    override fun createService(retrofit: Retrofit): RevolutService =
        retrofit.create(RevolutService::class.java)

    override val endpoint: String
        get() = BuildConfig.ENDPOINT

    // Replaced to mock for stability
    fun latestRates(base: String): Single<RatesResponse> = Single.fromCallable { rates(base) }
        .subscribeOn(Schedulers.io())


    fun rates(base: String): RatesResponse {

        return RatesResponse(
            Date(),
            base,
            rates()
                .reassignBy(base)
                .map { (k, v) -> CurrencyRate(k, v) }
                .sortedBy { it.code }
        )
    }

    fun Map<String, Double>.rate(base: String, to: String, howMuch: Double): Double {
        val eurRate = get("EUR")!!
        val baseRate = eurRate / get(base)!!
        val toRate = eurRate / get(to)!!
        return howMuch * toRate / baseRate
    }

    fun Map<String, Double>.reassignBy(base: String): Map<String, Double> {
        val curRate = get(base)!!
        val multiply = 1 / curRate
        return mapValues { (key) ->
            rate(base, key, curRate) * multiply
        }.filter { it.key != base }.also { println("$base = ${it.keys.joinToString()}") }
    }

    fun rates() = hashMapOf<String, Double>(
        "EUR" to 1.0,
        "AUD" to 0.6390668055781691,
        "BGN" to 0.4960947897183506,
        "BRL" to 0.21170435940669252,
        "CAD" to 0.6551870816746158,
        "CHF" to 0.9137546828157934,
        "CNY" to 0.1289235219780987,
        "CZK" to 0.03845899990614258,
        "DKK" to 0.13160341460664288,
        "GBP" to 1.1435578428835351,
        "HKD" to 0.10534162327004437,
        "HRK" to 0.13194394005349872,
        "HUF" to 0.0031316155726603353,
        "IDR" to 5.696259826939784E-5,
        "ILS" to 0.23279384373915094,
        "INR" to 0.01190151248128299,
        "ISK" to 0.007653696000978658,
        "JPY" to 0.007749055394686888,
        "KRW" to 7.420146017105112E-4,
        "MXN" to 0.04558155424762449,
        "MYR" to 0.21076770013954144,
        "NOK" to 0.10507823532828237,
        "NZD" to 0.579487159516107,
        "PHP" to 0.015970876736677974,
        "PLN" to 0.23183034253756268,
        "RON" to 0.20942809404362214,
        "RUB" to 0.012558814845068914,
        "SEK" to 0.09372379423891988,
        "SGD" to 0.6424120145357868,
        "THB" to 0.0256964692676862,
        "TRY" to 0.12780394886828816,
        "USD" to 0.8894735565540729,
        "ZAR" to 0.057501759938022895,
    ).mapValues { it.value * (0.99 + Random.nextInt(20).toDouble() / 1000) }
}