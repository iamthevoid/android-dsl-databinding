package iam.thevoid.noxml.demo.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface XeService {

    @GET("/currency/{code}")
    suspend fun currencyInfo(@Path("code") code : String) : String

}