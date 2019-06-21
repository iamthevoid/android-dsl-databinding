package thevoid.iam.ankoobservablecomponents.data.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import thevoid.iam.ankoobservablecomponents.BuildConfig
import thevoid.iam.ankoobservablecomponents.BuildConfig.ENDPOINT
import java.util.concurrent.TimeUnit

abstract class Api<S> {

    abstract val endpoint: String

    protected abstract fun createService(retrofit: Retrofit): S

    private val client by lazy {
        OkHttpClient.Builder()
            .followSslRedirects(true)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    val service by lazy { createService(retrofit) }

}