package com.renhuan.rhutils

import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import com.example.rhutils.R
import com.example.rhutils.databinding.FragmentBlank4Binding
import com.just.agentweb.AgentWeb
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.CenterPopupView
import com.renhuan.utils.base.RBaseFragment
import com.renhuan.utils.setOnSafeClickListener
import com.renhuan.utils.toast
import kotlinx.coroutines.launch


class BlankFragment4 : RBaseFragment<FragmentBlank4Binding>() {


    private val popu by lazy {
        object : CenterPopupView(requireActivity()) {

            private lateinit var popouBinding: FragmentBlank4Binding

            override fun getImplLayoutId(): Int {
                return R.layout.fragment_blank4
            }

            override fun onCreate() {
                super.onCreate()
                popouBinding = FragmentBlank4Binding.bind(popupImplView)
                popouBinding.btn.setOnSafeClickListener {
                    "dddddddddd".toast()
                }
            }
        }
    }


    companion object {
        fun getInstance(): BlankFragment4 {
            return BlankFragment4()
        }
    }

    override fun initView() {

        binding.btn.setOnSafeClickListener {
            XPopup.Builder(requireActivity())
                .asCustom(popu)
                .show()
//            LoginInterceptCoroutinesManager.get().checkLogin {
//                "去个人中心页面".toast()
//            }
        }


        AgentWeb.with(this)
            .setAgentWebParent(binding.ll, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go("http://124.225.164.142:9990/distH5/")

    }


    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
//            var requestDemo = Api.requestDemo(this)
//            var requestDemo1 = Api.requestDemo1(this)
//            requestDemo.tryAwait {
//                it.myThrowable()
//            }
//
//            requestDemo1.tryAwait {
//                it.myThrowable()
//            }

//            var requestDemo = Api.requestDemo()
//            Api.requestDemo1(0) {
//
//            }?.datas.let {
//
//            }

        }
    }

}