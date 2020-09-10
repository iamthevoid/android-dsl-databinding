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

//    suspend fun latest(base : String) : RatesResponse = try {
//        service.latest(base)
//    } catch (e : Exception) {
//        delay(2000)
//        latest(base)
//    }

//    fun latest(base : String) : Single<RatesResponse> = service.latest(base)

    // Replaced to mock for stability
    fun latest(base: String): Single<RatesResponse> = Single.fromCallable { rates(base) }
        .subscribeOn(Schedulers.io())
        .doOnSuccess { println(it) }


    fun rates(base: String): RatesResponse {
        val rate = currencies[base] ?: throw IllegalArgumentException("Unknown base $base")

        val result = mutableMapOf<String, Double>().apply {
            currencies.map { it.key to it.value }.forEach { (code, coef) ->
                if (code != base)
                    put(code, rate / coef * (Random.nextDouble(0.96, 1.04)))
            }
        }.map { CurrencyRate(it.key, it.value) }

        return RatesResponse(Date(), base, result)
    }

    val currencies: Map<String, Double> = mapOf(
        "AUD" to 1.6182,
        "BGN" to 1.9579,
        "BRL" to 4.797,
        "CAD" to 1.5355,
        "CHF" to 1.1287,
        "CNY" to 7.9537,
        "CZK" to 25.743,
        "DKK" to 7.4648,
        "EUR" to 1.0,
        "GBP" to 0.89922,
        "HKD" to 9.1423,
        "HRK" to 7.4422,
        "HUF" to 326.84,
        "IDR" to 17342.0,
        "ILS" to 4.1751,
        "INR" to 83.808,
        "ISK" to 127.94,
        "JPY" to 129.69,
        "KRW" to 1306.2,
        "MXN" to 22.39,
        "MYR" to 4.8172,
        "NOK" to 9.7866,
        "NZD" to 1.7652,
        "PHP" to 62.66,
        "PLN" to 4.323,
        "RON" to 4.6435,
        "RUB" to 79.661,
        "SEK" to 10.602,
        "SGD" to 1.6017,
        "THB" to 38.171,
        "TRY" to 7.6365,
        "USD" to 1.1647,
        "ZAR" to 17.843
    )
}