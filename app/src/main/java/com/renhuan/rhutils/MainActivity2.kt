package com.renhuan.rhutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rhutils.R
import com.example.rhutils.databinding.ActivityMain2Binding
import com.example.rhutils.databinding.ActivityMainBinding
import com.renhuan.utils.Ext
import com.renhuan.utils.base.RBaseActivity
import com.renhuan.utils.postDelayeds
import me.jingbin.library.adapter.BaseByViewHolder
import me.jingbin.library.adapter.BaseRecyclerAdapter

class MainActivity2 : RBaseActivity<ActivityMain2Binding>() {

    private var pageNum = 0

    private val mAdapter by lazy {
        object : BaseRecyclerAdapter<Int>(android.R.layout.simple_list_item_1) {
            override fun bindView(holder: BaseByViewHolder<Int>, bean: Int, position: Int) {
                holder.getView<TextView>(android.R.id.text1).text = bean.toString()
            }
        }
    }

    override fun initView() {

//        binding.byRecyclerView.let {
//            it.setEmptyView(R.layout.layout_empty)
//            it.adapter = mAdapter
//            it.setOnRefreshListener {
//                pageNum = 1
//                getData()
//            }
//            it.setOnLoadMoreListener(true, 5) {
//                println("----------我是最后第五条，准备预加载,第${pageNum}页")
//                Ext.getHandler().postDelayeds(1000) {
//
//                    if (pageNum == 3) {
//                        pageNum++
//                        binding.byRecyclerView.loadMoreFail()
//                        return@postDelayeds
//                    }
//
//                    if (pageNum == 5) {
//                        binding.byRecyclerView.loadMoreEnd()
//                        return@postDelayeds
//                    }
//
//                    pageNum++
//                    getData()
//                }
//            }
//        }
    }

    private fun getData() {
        val pageSize = 25
        val list = arrayListOf<Int>()
        for (i in (pageNum - 1) * pageSize..pageNum * pageSize) {
            list.add(i)
        }
        mAdapter.setPageData(pageNum == 1, list, R.layout.layout_empty)
    }
}