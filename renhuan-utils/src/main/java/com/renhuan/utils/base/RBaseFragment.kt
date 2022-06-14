package com.renhuan.utils.base

/**
 * created by renhuan
 * time : 2022/6/13 11:44
 * describe :
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.renhuan.utils.eventBus.REventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

abstract class RBaseFragment<VB : ViewBinding> : Fragment() {

    lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method =
            aClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, container, false) as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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