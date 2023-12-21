package com.renhuan.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.CycleInterpolator
import android.widget.TextView
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.renhuan.utils.other.ApplicationContentProvider
import com.renhuan.utils.other.DrawableTintHelper

/**
 * created by renhuan
 * time : 2022/3/28 13:20
 * describe : 各种方便的工具方法
 */
object Ext {

    private var handler = Handler(Looper.getMainLooper())

    /**
     * 获取创建在主线程上的Handler对象。
     */
    fun getHandler(): Handler {
        return handler
    }

    /**
     * 获取全局Context
     */
    fun getContext(): Context {
        return ApplicationContentProvider.mAppContext
    }

    /**
     * 获取资源文件中定义的字符串。
     */
    fun getString(resId: Int): String {
        return getContext().resources.getString(resId)
    }

    /**
     * 获取资源文件中定义的尺寸。
     */
    fun getDimension(colorId: Int): Float {
        return getContext().resources.getDimension(colorId)
    }

    /**
     * 获取资源文件中定义的颜色。
     */
    fun getColor(colorResId: Int): Int {
        return ContextCompat.getColor(getContext(), colorResId)
    }

    /**
     * 获取资源文件中定义的Drawable。
     */
    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(getContext(), resId)
    }

    /**
     * 按两次返回桌面
     * 用法：
     * override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean{
     *    return Ext.onKeyDown(keyCode, event, super.onKeyDown(keyCode, event))
     *  }
     */
    private var firstTime: Long = 0

    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 1000) {
                "再按一次退出".toast()
                firstTime = System.currentTimeMillis()
            } else {
                AppUtils.exitApp()
            }
            return true
        }
        return false
    }
}

/**
 * 吐司toast
 */
fun String.toast() {
    if (this.isNotBlank()) {
        ToastUtils.getDefaultMaker()
            .setMode(ToastUtils.MODE.DARK)
            .show(this)
    }
}

/**
 * 安全点击 防止多次点击 1s之内之内只能点击一次
 */
inline fun View.setOnSafeClickListener(crossinline action: () -> Unit) {
    var lastClick = 0L
    setOnClickListener {
        val gap = System.currentTimeMillis() - lastClick
        lastClick = System.currentTimeMillis()
        if (gap < 1000) return@setOnClickListener
        action()
    }
}

/**
 * Handler 延迟功能
 */
inline fun Handler.postDelayeds(
    delayMillis: Long,
    crossinline action: () -> Unit
): Runnable {
    return Runnable { action() }.apply { postDelayed(this, delayMillis) }
}

/**
 * Handler 定时器功能
 */
inline fun Handler.postDelayeds(
    delayMillis: Long,
    period: Long,
    crossinline action: (Long, Runnable) -> Unit
): Runnable {
    var currentTime = 0L
    return object : Runnable {
        override fun run() {
            postDelayed(this, period)
            currentTime += period
            action(currentTime, this)
        }
    }.apply<Runnable> { postDelayed(this, delayMillis) }
}

/**
 * 判断 TextView 是否为空
 * 用法：phone.checkEmpty("手机号不能为空") ?: return,
 */
fun TextView.checkEmpty(message: String): String? {
    val text = this.text.toString().trim()
    if (text.isEmpty()) {
        message.toast()
        return null
    }
    return text
}

/**  获取 Textview 的值   */
fun TextView.text(): String {
    return text.toString().trim()
}

/**  复制到剪切板   */
fun String.copy() {
    ClipboardUtils.copyText(this)
    "已复制：$this".toast()
}

/**
 * 给drawable着色，返回新的drawable
 */
fun Drawable.tintColor(colorId: Int): Drawable =
    DrawableTintHelper.tintDrawable(this, colorId)

fun View.setScale(@FloatRange(from = 0.0, to = 1.0) percent: Float = 0.85f) {
    setOnTouchListener { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                AnimatorSet().apply {
                    playTogether(
                        ObjectAnimator.ofFloat(this@setScale, "scaleX", 1.0f, percent).apply {
                            interpolator = CycleInterpolator(0.5f)
                        },
                        ObjectAnimator.ofFloat(this@setScale, "scaleY", 1.0f, percent).apply {
                            interpolator = CycleInterpolator(0.5f)
                        }
                    )
                    duration = 300
                    start()
                }
            }
            MotionEvent.ACTION_UP -> {
                view.performClick()
            }
        }
        true
    }
}