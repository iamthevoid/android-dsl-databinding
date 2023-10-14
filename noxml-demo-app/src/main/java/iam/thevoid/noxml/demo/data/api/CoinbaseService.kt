package iam.thevoid.noxml.demo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinbaseService {

    @GET("/v2/currencies")
    fun currencyInfo() : Single<CoinbaseResponse>

}