package com.renhuan.utils

import android.text.method.ScrollingMovementMethod
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.BarUtils
import com.renhuan.utils.base.RBaseActivity
import com.renhuan.utils.databinding.ActivityCrashBinding

class CrashActivity : RBaseActivity<ActivityCrashBinding>() {

    private val mCaocConfig by lazy { CustomActivityOnCrash.getConfigFromIntent(intent) }

    override fun initView() {
        BarUtils.setStatusBarLightMode(this, true)
        BarUtils.transparentStatusBar(this)
        BarUtils.addMarginTopEqualStatusBarHeight(binding.tvSale)
        binding.apply {
            tvLog.movementMethod = ScrollingMovementMethod.getInstance()
            tvLog.text = CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@CrashActivity, intent)
            tvShowLog.setOnSafeClickListener {
                tvLog.text().copy()
                tvShowLog.text = "日志已复制"
                tvShowLog.isEnabled = false
                "日志已复制".toast()
            }

            tvExit.setOnSafeClickListener {
                CustomActivityOnCrash.closeApplication(this@CrashActivity, mCaocConfig!!)
            }

            tvRestart.setOnSafeClickListener {
                CustomActivityOnCrash.restartApplication(this@CrashActivity, mCaocConfig!!)
            }
        }

    }


}