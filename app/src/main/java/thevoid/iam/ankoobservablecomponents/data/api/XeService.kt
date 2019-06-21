package thevoid.iam.ankoobservablecomponents.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import thevoid.iam.ankoobservablecomponents.data.api.model.RatesResponse

interface XeService {

    @GET("/currency/{code}")
    fun currencyInfo(@Path("code") code : String): Single<String>

}