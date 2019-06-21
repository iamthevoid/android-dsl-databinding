package thevoid.iam.ankoobservablecomponents.data.api

import retrofit2.Retrofit
import thevoid.iam.ankoobservablecomponents.BuildConfig

object XeApi : Api<XeService>() {

    override fun createService(retrofit: Retrofit): XeService = retrofit.create(XeService::class.java)

    override val endpoint: String
        get() = "https://www.xe.com"


}