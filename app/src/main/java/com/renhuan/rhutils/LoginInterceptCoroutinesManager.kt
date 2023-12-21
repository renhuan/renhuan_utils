package com.renhuan.rhutils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.SPUtils
import com.renhuan.utils.Ext
import com.renhuan.utils.postDelayeds
import com.renhuan.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

/**
 * created by renhuan
 * time : 2022/10/26 09:01
 * describe : 登录拦截
 */
class LoginInterceptCoroutinesManager private constructor() : DefaultLifecycleObserver, CoroutineScope by MainScope() {

    companion object {
        private var instance: LoginInterceptCoroutinesManager? = null
            get() {
                if (field == null) {
                    field = LoginInterceptCoroutinesManager()
                }
                return field
            }

        fun get(): LoginInterceptCoroutinesManager {
            return instance!!
        }
    }

    private lateinit var sendChannel: SendChannel<Boolean>


    private fun isLogin() = SPUtils.getInstance().getBoolean("login")

    fun checkLogin(nextAction: () -> Unit) {

        launch {

            if (isLogin()) {
                nextAction()
                return@launch
            }

            //如果没有登录，先去登录页面
            "去登录页面,登录中".toast()
            Ext.getHandler().postDelayeds(5000) {
                "登录完成".toast()
                SPUtils.getInstance().put("login", true)
                LoginInterceptCoroutinesManager.get().loginFinished()
            }

            sendChannel = actor {
                val isLogin = receive()
                if (isLogin) {
                    nextAction()
                }
            }
        }
    }


//    fun a (){
//        LoginInterceptCoroutinesManager.get().checkLogin {
//
//        }
//    }

    fun loginFinished() {

        launch {
            if (this@LoginInterceptCoroutinesManager::sendChannel.isInitialized) {
                sendChannel.send(isLogin())
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
    }


}