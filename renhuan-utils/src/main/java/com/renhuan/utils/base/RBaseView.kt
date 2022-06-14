package com.renhuan.utils.base

/**
 * created by renhuan
 * time : 2022/6/13 11:34
 * describe :
 */
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.renhuan.utils.eventBus.REventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

/**
 */
abstract class RBaseView<VB : ViewBinding>(context: Context, attributeSet: AttributeSet?) :
    FrameLayout(context, attributeSet), LifecycleObserver, LifecycleOwner {

    private var mContext = context

    /**
     * 子类重写initView()
     */
    abstract fun initView()
    open fun initData() {}
    open fun initListener() {}

    /**
     * 使用反射得到viewbinding的class
     */
    val binding by lazy {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, LayoutInflater.from(context)) as VB
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //初始化数据
        if (isRegisterEventBus) {
            REventBus.register(this)
        }
        if (mContext is LifecycleOwner) {
            (mContext as LifecycleOwner).lifecycle.addObserver(this)
        }
        addView(binding.root)
        initView()
        initData()
        initListener()
    }

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


    /**
     * 具有生命周期
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        if (isRegisterEventBus) {
            REventBus.unregister(this)
        }
        if (mContext is LifecycleOwner) {
            (mContext as LifecycleOwner).lifecycle.removeObserver(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }
}