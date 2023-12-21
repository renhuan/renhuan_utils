package com.renhuan.rhutils

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.rhutils.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.ssl.HttpsUtils
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


/**
 * created by renhuan
 * time : 2022/9/23 16:40
 * describe :
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RxHttpPlugins
            .init(getDefaultOkHttpClient())
            .setDebug(BuildConfig.DEBUG, true)

    }

    //Default OkHttpClient object in RxHttp
    private fun getDefaultOkHttpClient(): OkHttpClient {
        val sslParams = HttpsUtils.getSslSocketFactory()
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        ChuckerInterceptor.Builder(this@App)
                            .collector(ChuckerCollector(this@App))
                            .maxContentLength(250000L)
                            .redactHeaders(emptySet())
                            .alwaysReadResponseBody(false)
                            .build()
                    )
                    addInterceptor(OkHttpProfilerInterceptor())
                }
            }
            .build()
    }
}