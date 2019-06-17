package thevoid.iam.ankoobservablecomponents.recycler

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    fun latest(@Query("base") base: String): Single<RatesResponse>

}