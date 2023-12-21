package com.renhuan.utils.base

import com.renhuan.utils.eventBus.REventBus

/**
 * created by renhuan
 * time : 2022/6/13 11:41
 * describe :
 */
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

abstract class RBaseActivity<VB : ViewBinding> : AppCompatActivity() {
    /**
     * 使用反射得到viewbinding的class
     */
    val binding by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as VB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //注册eventbus
        if (isRegisterEventBus) {
            REventBus.register(this)
        }

        //一些重载方法
        initView()
        initData()
        initListener()

    }

    protected abstract fun initView()
    open fun initData() {}
    open fun initListener() {}

    /**
     * 子类重写是否注册事件分发
     */
    open val isRegisterEventBus: Boolean
        get() = false

    /**
     * 子类重写接收到分发到事件
     */
    open fun receiveEvent(event: Any) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventBus(event: Any?) {
        event?.let { receiveEvent(it) }
    }

    override fun onDestroy() {
        if (isRegisterEventBus) {
            REventBus.unregister(this)
        }
        super.onDestroy()
    }
}