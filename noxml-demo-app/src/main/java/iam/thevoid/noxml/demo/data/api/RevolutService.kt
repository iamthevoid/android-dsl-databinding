package iam.thevoid.noxml.demo.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import iam.thevoid.noxml.demo.data.api.model.RatesResponse

interface RevolutService {

    @GET("latest")
    suspend fun latest(@Query("base") base: String): RatesResponse

}