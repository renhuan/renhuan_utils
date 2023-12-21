package com.renhuan.rhutils

import android.graphics.BitmapFactory
import android.view.View
import com.alexvasilkov.foldablelayout.UnfoldableView
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading
import com.example.rhutils.R
import com.example.rhutils.databinding.FragmentBlank2Binding
import com.renhuan.utils.base.RBaseFragment
import me.jingbin.library.adapter.BaseByViewHolder
import me.jingbin.library.adapter.BaseRecyclerAdapter


class BlankFragment2 : RBaseFragment<FragmentBlank2Binding>() {

    private val mAdapter by lazy {
        object : BaseRecyclerAdapter<Int>(R.layout.item_img, arrayListOf(1, 2, 3, 4, 5)) {
            override fun bindView(holder: BaseByViewHolder<Int>, bean: Int, position: Int) {
                holder.addOnClickListener(R.id.img)
                if (position % 2 == 0) {
                    holder.setImageResource(R.id.img, R.mipmap.a)
                } else {
                    holder.setImageResource(R.id.img, R.mipmap.b)
                }
            }
        }
    }


    override fun initView() {

        binding.byRecyclerView.adapter = mAdapter
        binding.byRecyclerView.setOnItemChildClickListener { v, position ->
            if (position % 2 == 0) {
                binding.detailsImage.setImageResource(R.mipmap.a)
            } else {
                binding.detailsImage.setImageResource(R.mipmap.b)
            }
            binding.unfoldableView.unfold(v, binding.detailsLayout)
        }

        binding.unfoldableView.let {
            it.setFoldShading(GlanceFoldShading(BitmapFactory.decodeResource(resources, R.mipmap.unfold_glance)))
            it.setOnFoldingListener(object : UnfoldableView.SimpleFoldingListener() {
                override fun onUnfolding(unfoldableView: UnfoldableView?) {
                    super.onUnfolding(unfoldableView)
                    binding.listTouchInterceptor.isClickable = true;
                    binding.detailsLayout.visibility = View.VISIBLE;
                }

                override fun onUnfolded(unfoldableView: UnfoldableView?) {
                    super.onUnfolded(unfoldableView)
                    binding.listTouchInterceptor.isClickable = false;
                }

                override fun onFoldingBack(unfoldableView: UnfoldableView?) {
                    super.onFoldingBack(unfoldableView)
                    binding.listTouchInterceptor.isClickable = true;
                }

                override fun onFoldedBack(unfoldableView: UnfoldableView?) {
                    super.onFoldedBack(unfoldableView)
                    binding.listTouchInterceptor.isClickable = false;
                    binding.detailsLayout.visibility = View.INVISIBLE;
                }
            })
        }
    }

    companion object {
        fun getInstance(): BlankFragment2 {
            return BlankFragment2()
        }
    }

}