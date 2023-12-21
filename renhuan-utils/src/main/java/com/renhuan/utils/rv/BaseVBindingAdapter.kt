package com.renhuan.utils.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import me.jingbin.library.adapter.BaseByRecyclerViewAdapter
import java.lang.reflect.ParameterizedType


/**
 * 【ViewBinding】单一 item 类型 adapter
 * https://github.com/youlookwhat/ByRecyclerView
 */
abstract class BaseVBindingAdapter<T, B : ViewBinding?>(data: List<T>? = null) :
    BaseByRecyclerViewAdapter<T, BaseVBindingHolder<T, B>>(data) {

    init {
        initVBClass()
    }

    private var vbClass: Class<*>? = null

    private fun initVBClass() {
        val typeArr = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        for (type in typeArr) {
            val aClass = type as Class<*>
            if (ViewBinding::class.java.isAssignableFrom(aClass)) {
                vbClass = aClass
                return
            }
        }
        throw RuntimeException("你的适配器需要提供一个ViewBinding的泛型才能进行视图绑定")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVBindingHolder<T, B> {
        val method =
            vbClass?.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            )
        val binding = method?.invoke(null, LayoutInflater.from(parent.context), parent, false) as B
        return BaseViewHolder(binding) as BaseVBindingHolder<T, B>

    }

    inner class BaseViewHolder(binding: B) : BaseVBindingHolder<T?, B>(binding) {
        override fun onBindingView(holder: BaseVBindingHolder<*, *>?, bean: T?, position: Int) {
            if (holder != null && bean != null && binding != null) {
                bindView(holder, bean, binding, position)
            }
        }

        override fun onBindingViewPayloads(
            holder: BaseVBindingHolder<*, *>?,
            bean: T?,
            position: Int,
            payloads: List<Any>
        ) {
            if (holder != null && bean != null && binding != null) {
                bindViewPayloads(holder, bean, binding, position, payloads)
            }
        }
    }

    protected abstract fun bindView(holder: BaseVBindingHolder<*, *>, bean: T, binding: B, position: Int)

    /**
     * 局部刷新，非必须
     */
    protected fun bindViewPayloads(
        holder: BaseVBindingHolder<*, *>,
        bean: T,
        binding: B,
        position: Int,
        payloads: List<Any>
    ) {
        /*
         * fallback to onBaseBindView(holder, bean,position) if app does not override this method.
         * 如果不覆盖 bindViewPayloads() 方法，就走 bindView()
         */
        bindView(holder, bean, binding, position)
    }
}