package iam.thevoid.noxml.demo.data.api

import retrofit2.Retrofit

object XeApi : Api<XeService>() {

    override fun createService(retrofit: Retrofit): XeService = retrofit.create(XeService::class.java)

    override val endpoint: String
        get() = "https://www.xe.com"


}