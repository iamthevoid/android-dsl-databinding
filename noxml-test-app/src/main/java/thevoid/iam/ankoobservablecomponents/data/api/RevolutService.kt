package thevoid.iam.ankoobservablecomponents.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import thevoid.iam.ankoobservablecomponents.data.api.model.RatesResponse

interface RevolutService {

    @GET("latest")
    fun latest(@Query("base") base: String): Single<RatesResponse>

}