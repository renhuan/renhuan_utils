package com.renhuan.rhutils

import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.example.rhutils.R
import com.example.rhutils.databinding.FragmentBlankBinding
import com.example.rhutils.databinding.ItemGridBinding
import com.renhuan.rhutils.extension.finish
import com.renhuan.rhutils.extension.glide
import com.renhuan.rhutils.extension.setSkeleton
import com.renhuan.rhutils.http.Api
import com.renhuan.utils.Ext
import com.renhuan.utils.base.RBaseFragment
import com.renhuan.utils.postDelayeds
import com.renhuan.utils.rv.BaseVBindingAdapter
import com.renhuan.utils.rv.BaseVBindingHolder
import com.renhuan.utils.toast
import kotlinx.coroutines.launch
import me.jingbin.library.skeleton.ByRVItemSkeletonScreen
import me.simple.view.ImageAdapter

class BlankFragment : RBaseFragment<FragmentBlankBinding>() {

    private val mAdapter by lazy {

        object : BaseVBindingAdapter<Data, ItemGridBinding>() {

            override fun bindView(
                holder: BaseVBindingHolder<*, *>,
                bean: Data,
                binding: ItemGridBinding,
                position: Int
            ) {

                binding.apply {
                    tvDesc.text = bean.title

                    val imgs = arrayListOf<Int>().apply {
                        repeat(position + 1) { add(R.mipmap.a) }
                    }
//                    nineGridView.adapter = ImageAdapter(imgs) { imageView, item, position ->
//                        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
////                        if (imgs.size == 1) {
////                            imageView.updateLayoutParams<ViewGroup.LayoutParams> {
////                                this.height = SizeUtils.dp2px(400f)
////                                this.width = SizeUtils.dp2px(250f)
////                            }
////                        }
//                        imageView.glide("https://images.pexels.com/photos/13516347/pexels-photo-13516347.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
//                    }.apply {
//                        onItemViewClick = { item, position ->
//                            ("ItemView click -- $position").toast()
//                        }
//                        onExtraViewClick = { position ->
//                            ("ExtraView click  $position").toast()
//                        }
//                    }
                }

            }
        }
    }

    private var pageNum = 661

    private var skeleton: ByRVItemSkeletonScreen? = null


    override fun initView() {
        binding.byRecyclerView.let {
            it.adapter = mAdapter
            it.setOnRefreshListener {
                pageNum = 661
                getData()
            }
            it.setOnLoadMoreListener {
                pageNum += 1
                getData()
            }
            skeleton = mAdapter.setSkeleton()
        }
    }


    override fun onResume() {
        super.onResume()
//        binding.byRecyclerView.isRefreshing = true
        getData()
    }

    private fun getData() {
        Ext.getHandler().postDelayeds(2000) {
            lifecycleScope.launch {
                Api.requestDemo1(pageNum).apply {
                    mAdapter.finish(this?.datas)
                    skeleton?.hide()
                }
            }
        }
    }


    companion object {
        fun getInstance(): BlankFragment {
            return BlankFragment()
        }
    }

}