package thevoid.iam.ankoobservablecomponents.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import thevoid.iam.ankoobservablecomponents.data.api.butik.CatalogResponse

interface ButikService {

    @GET("/api/v4/catalog")
    @Headers("uuid: 777")
    fun catalog(@QueryMap map: Map<String, String>): Single<CatalogResponse>

}