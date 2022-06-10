package com.renhuan.rhutils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.rhutils.R
import com.renhuan.utils.Ext
import com.renhuan.utils.activitymessenger.get
import com.renhuan.utils.adapter.MyFragmentPagerAdapter
import com.renhuan.utils.setScale
import com.renhuan.utils.tablayout.TabLayout
import com.renhuan.utils.tintColor
import com.renhuan.utils.toast
import com.wuyr.activitymessenger.ActivityMessenger

class MainActivity : AppCompatActivity() {

    //需要跳转到Activity定义静态方法
    companion object {
        fun startAction(context: Context, value: String) {
            ActivityMessenger.startActivity<MainActivity>(context)
//            ActivityMessenger.startActivity<MainActivity>(context, "key" to value)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //跳转的时候调用
//        MainActivity.startAction(this,"value")


//        //赋值
//        MMKVRepository.str = "张三"
//        MMKVRepository.num = 100
//        MMKVRepository.b = false
//        //取值
//        println("---------" + MMKVRepository.str)
//        println("---------" + MMKVRepository.num)
//        println("---------" + MMKVRepository.b)


        findViewById<ImageView>(R.id.imageView).setImageDrawable(
            Ext.getDrawable(R.mipmap.ic_close)?.tintColor(Ext.getColor(R.color.red))
        )

        findViewById<ImageView>(R.id.imageView2).setImageDrawable(
            Ext.getDrawable(R.mipmap.ic_close)?.tintColor(Ext.getColor(R.color.purple_200))
        )


//        Ext.getHandler() //获取Handle示例
//        Ext.getHandler().postDelayeds(1000) {}//延迟1s 运行 （延时器）
//        Ext.getHandler().postDelayeds(1000, 1000) { time, runnable -> } //延迟1s并每秒运行 （定时器）
//        Ext.getContext() //获取全局context
//        Ext.getString(R.string.app_name) //获取资源字符串
//        Ext.getColor(R.color.red) //获取资源颜色
//        Ext.getDimension(R.dimen.dp_10) //获取资源尺寸
//        Ext.getDrawable(R.mipmap.ic_close) //获取资源drawable
//        Ext.onKeyDown(keyCode, event) //按两次返回桌面，重写MainActivity的onKeyDown(keyCode: Int, event: KeyEvent?)方法 返回 return Ext.onKeyDown(keyCode, event)
//        "这是提示".toast() //toast
//        view.setOnSafeClickListener{} //安全点击 防止多次点击 1s之内之内只能点击一次
//        textview.checkEmpty("") //判断TextView是否为空,用法：phone.checkEmpty("手机号不能为空") ?: return
//        textview.text() //获取Textview的值
//        string.copy() //复制到剪切板
//        drawable.tintColor(color) // 给drawable着色，返回新的drawable


        val viewPager = findViewById<ViewPager>(R.id.vp)
        val tab = findViewById<TabLayout>(R.id.tab)
        val tab1 = findViewById<TabLayout>(R.id.tab1)
        val tab2 = findViewById<TabLayout>(R.id.tab2)

        viewPager.adapter = MyFragmentPagerAdapter(
            supportFragmentManager,
            arrayListOf(
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance()
            ),
            arrayListOf("张三", "李四", "张无忌", "Java", "Android")
        )
//样式1
        tab.setupWithViewPager(viewPager)

//样式2 加图片（所有tab都显示）
        tab1.setIcons(
            arrayListOf(
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher
            ), false
        )
        tab1.setupWithViewPager(viewPager)

//样式3 加图片（当前选中tab显示）
        tab2.setIcons(
            arrayListOf(
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
            ), true
        )
        tab2.setupWithViewPager(viewPager)
    }

    class DemoFragment : Fragment() {
        companion object {
            fun getInstance(): DemoFragment {
                return DemoFragment()
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return LayoutInflater.from(requireActivity()).inflate(R.layout.fragment_demo, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            view.findViewById<View>(R.id.button).setOnClickListener { "点击了".toast() }
        }
    }
}