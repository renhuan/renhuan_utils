package com.renhuan.rhutils.http

import com.blankj.utilcode.util.ActivityUtils
import com.example.rhutils.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.renhuan.rhutils.Bean
import rxhttp.awaitResult
import rxhttp.onStart
import rxhttp.wrapper.annotation.DefaultDomain
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse


/**
 *
 * time : 2021/2/1 17:02
 * describe :
 */
object Api {

    //返回的json code为多少时候表示请求成功
    const val CODE = 0

    //每页多少条数据
    const val pageSize = 20

    //域名
    @DefaultDomain
    var BASE_URL = "https://www.wanandroid.com"

    private val asLoading by lazy {
        XPopup.Builder(ActivityUtils.getTopActivity())
            .hasShadowBg(false)
            .dismissOnTouchOutside(false)
            .asLoading("加载中...", R.layout.loading, LoadingPopupView.Style.ProgressBar)
    }

    private fun showLoading() {
        asLoading?.show()
    }

    private fun dismissLoading() {
        asLoading?.smartDismiss()
    }

    private suspend fun <T> Await<T>.request(
        isShowLoading: Boolean = true,
        onError: ((Throwable) -> Unit)? = null
    ): T? =
        onStart { if (isShowLoading) showLoading() }
            .awaitResult {
                if (isShowLoading) dismissLoading()
            }
            .onFailure {
                if (isShowLoading) dismissLoading()
                onError?.invoke(it)
                it.myThrowable()
            }.getOrNull()


//    suspend fun requestDemo(scope: CoroutineScope): Deferred<String> {
//        return RxHttp.get("/project/tree/json")
//            .toResponse<String>()
//            .async(scope)
//    }
//
//    suspend fun requestDemo1(scope: CoroutineScope): Deferred<String> {
//        return RxHttp.get("/navi/json")
//            .toResponse<String>()
//            .async(scope)
//    }

    //    suspend fun requestDemo(): String? {
//        return RxHttp.get("/project/tree/json")
//            .toResponse<String>()
//            .request()
//    }
//
    suspend fun requestDemo1(pageNum: Int): Bean? {
        return RxHttp.get("article/list/${pageNum}/json")
            .toResponse<Bean>()
            .request(false)
    }

}