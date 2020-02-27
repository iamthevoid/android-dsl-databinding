package iam.thevoid.noxml.demo.data.api

import iam.thevoid.noxml.demo.BuildConfig
import retrofit2.Retrofit
import iam.thevoid.noxml.demo.data.api.model.RatesResponse
import kotlinx.coroutines.delay

object RevolutApi : Api<RevolutService>() {

    override fun createService(retrofit: Retrofit): RevolutService = retrofit.create(RevolutService::class.java)

    override val endpoint: String
        get() = BuildConfig.ENDPOINT

    suspend fun latest(base : String) : RatesResponse = try {
        service.latest(base)
    } catch (e : Exception) {
        delay(2000)
        latest(base)
    }

}