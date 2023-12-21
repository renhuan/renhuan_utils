package com.renhuan.rhutils.http

import com.google.gson.JsonSyntaxException
import com.renhuan.utils.toast
import kotlinx.coroutines.TimeoutCancellationException
import rxhttp.wrapper.exception.CacheReadFailedException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException

/**
 *
 * time : 2020/7/9 17:50
 * describe :
 */

val Throwable.code: Int
    get() =
        when (this) {
            is HttpStatusCodeException -> this.statusCode //Http状态码异常
            is ParseException -> this.errorCode.toIntOrNull() ?: -1     //业务code异常
            else -> -1
        }


val Throwable.msg: String
    get() {
        return when (this) {
            is UnknownHostException -> { //网络异常
                "当前无网络，请检查你的网络设置"
            }
            is SocketTimeoutException, is TimeoutException, is TimeoutCancellationException -> {
                "连接超时,请稍后再试"
            }
            is ConnectException, is SSLHandshakeException -> {
                "网络不给力，请稍候重试！"
            }
            is HttpStatusCodeException -> {               //请求失败异常
                "Http状态码异常(${this.code})"
            }
            is JsonSyntaxException -> {  //请求成功，但Json语法异常,导致解析失败
                "数据解析失败,请检查数据是否正确"
            }
            is ParseException -> {       // ParseException异常表明请求成功，但是数据不正确
                this.message ?: errorCode   //msg为空，显示code
            }
            is CacheReadFailedException -> {
                ""
            }
            is SocketException -> {
                "网络已断开，请检查网络连接"
            }
            else -> {
                "请求失败，请稍后再试"
            }
        }
    }


//rxScope网络请求 异常处理
fun Throwable.myThrowable() {
    //表示未登录 跳转登录页面
    if (this.code == 401) {
//            BaseActivity.unLoginTip()
    } else {
        this.msg.toast()
    }

}