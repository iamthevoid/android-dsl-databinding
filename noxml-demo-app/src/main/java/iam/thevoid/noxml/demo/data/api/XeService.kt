package iam.thevoid.noxml.demo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface XeService {

    @GET("/currency/{code}")
    fun currencyInfo(@Path("code") code : String) : Single<String>

}