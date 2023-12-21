package com.renhuan.rhutils

import android.content.Context
import android.view.KeyEvent
import com.example.rhutils.R
import com.example.rhutils.databinding.ActivityMainBinding
import com.renhuan.utils.Ext
import com.renhuan.utils.adapter.MyFragmentPagerAdapter
import com.renhuan.utils.base.RBaseActivity
import com.wuyr.activitymessenger.ActivityMessenger


class MainActivity : RBaseActivity<ActivityMainBinding>() {

    //需要跳转到Activity定义静态方法
    companion object {
        fun startAction(context: Context, value: String) {
            ActivityMessenger.startActivity<MainActivity>(context)
//            ActivityMessenger.startActivity<MainActivity>(context, "key" to value)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return Ext.onKeyDown(keyCode, event)
    }

    override fun initView() {

        binding.vp.adapter = MyFragmentPagerAdapter(
            supportFragmentManager,
            arrayListOf(
                DemoFragment.getInstance(),
                BlankFragment.getInstance(),
                BlankFragment2.getInstance(),
                BlankFragment3.getInstance(),
                BlankFragment4.getInstance()
            ),
            arrayListOf("张三", "李四", "张无忌", "Java", "Android")
        )

        //样式1
        binding.tab.setupWithViewPager(binding.vp)

        //样式2 加图片（所有tab都显示）
        binding.tab1.setIcons(
            arrayListOf(
                R.drawable.a,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher
            ), false
        )
        binding.tab1.setupWithViewPager(binding.vp)

        //样式3 加图片（当前选中tab显示）
        binding.tab2.setIcons(
            arrayListOf(
                R.drawable.a,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
            ), true
        )
        binding.tab2.setupWithViewPager(binding.vp)

    }


}