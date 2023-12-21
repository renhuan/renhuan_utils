package com.renhuan.rhutils

import com.example.rhutils.databinding.FragmentBlank3Binding
import com.renhuan.utils.base.RBaseFragment

class BlankFragment3 : RBaseFragment<FragmentBlank3Binding>() {

    override fun initView() {
        binding.playView.setOnClickListener {
            binding.playView.setLoading(!binding.playView.isPlaying)
            if (binding.playView.isPlaying) {
                binding.playView.pause()
            } else {
                binding.playView.play()
            }
        }
    }


    companion object {
        fun getInstance(): BlankFragment3 {
            return BlankFragment3()
        }
    }

}