package thevoid.iam.ankoobservablecomponents.data.api

import retrofit2.Retrofit

object ButikApi : Api<ButikService>() {
    override val endpoint: String
        get() = "https://www.butik.ru"

    override fun createService(retrofit: Retrofit): ButikService =
        retrofit.create(ButikService::class.java)

    fun catalog(page: Int) =
        service.catalog(
            mapOf(
                "type" to "catalog",
                "category" to "9",
                "order" to "popular",
                "page" to "$page",
                "per_page" to "200"
            )
        )
}