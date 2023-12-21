package com.renhuan.rhutils

import android.content.Context
import android.util.AttributeSet
import com.example.rhutils.databinding.FragmentDemoBinding
import com.renhuan.utils.base.RBaseView

/**
 * created by renhuan
 * time : 2022/7/13 10:41
 * describe :
 */
class MyView(context: Context, attributeSet: AttributeSet) : RBaseView<FragmentDemoBinding>(context, attributeSet) {
    override fun initView() {

    }

    override fun onResume() {
        super.onResume()
        println("-----------------------MyView onResume")
    }

    override fun onStop() {
        super.onStop()
        println("-----------------------MyView onStop")
    }
}