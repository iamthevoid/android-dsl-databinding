package iam.thevoid.noxml.demo.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import iam.thevoid.noxml.demo.data.api.model.RatesResponse
import io.reactivex.Single

interface RevolutService {

    @GET("latest")
    fun latest(@Query("base") base: String): Single<RatesResponse>

}