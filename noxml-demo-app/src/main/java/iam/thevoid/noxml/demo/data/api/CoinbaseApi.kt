package iam.thevoid.noxml.demo.data.api

import retrofit2.Retrofit

object CoinbaseApi : Api<CoinbaseService>() {

    override fun createService(retrofit: Retrofit): CoinbaseService = retrofit.create(CoinbaseService::class.java)

    fun currencyInfoShared() = service.currencyInfo().toObservable().share()

    override val endpoint: String
        get() = "https://api.coinbase.com"


}