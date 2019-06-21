package thevoid.iam.ankoobservablecomponents.data.api

import retrofit2.Retrofit
import thevoid.iam.ankoobservablecomponents.BuildConfig

object RevolutApi : Api<RevolutService>() {

    override fun createService(retrofit: Retrofit): RevolutService = retrofit.create(RevolutService::class.java)

    override val endpoint: String
        get() = BuildConfig.ENDPOINT


}