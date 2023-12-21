package com.renhuan.utils.rv

import androidx.viewbinding.ViewBinding
import me.jingbin.library.adapter.BaseByViewHolder

/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
abstract class BaseVBindingHolder<T, B : ViewBinding?>(val binding: B) : BaseByViewHolder<T>(
    binding!!.root
) {
    override fun onBaseBindView(holder: BaseByViewHolder<T>, bean: T, position: Int) {
        onBindingView(this, bean, position)
    }

    override fun onBaseBindViewPayloads(holder: BaseByViewHolder<T>, bean: T, position: Int, payloads: List<Any>) {
        onBindingViewPayloads(this, bean, position, payloads)
    }

    protected abstract fun onBindingView(holder: BaseVBindingHolder<*, *>?, bean: T, position: Int)
    protected open fun onBindingViewPayloads(
        holder: BaseVBindingHolder<*, *>?,
        bean: T,
        position: Int,
        payloads: List<Any>
    ) {
        /*
         * fallback to onBindingViewPayloads(holder, bean,position) if app does not override this method.
         * 如果不覆盖 bindViewPayloads() 方法，就走 onBindingView()
         */
        onBindingView(holder, bean, position)
    }
}