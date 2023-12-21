package com.renhuan.rhutils.extension

import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.example.rhutils.R
import me.jingbin.library.adapter.BaseByRecyclerViewAdapter
import me.jingbin.library.skeleton.ByRVItemSkeletonScreen
import me.jingbin.library.skeleton.BySkeleton

/**
 * created by renhuan
 * time : 2022/9/26 15:19
 * describe :
 */

fun ImageView.glide(url: String) {
    Glide.with(ActivityUtils.getTopActivity())
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.sp_loading)
        .into(this)
}

/**
 * byrecyclerview的扩展  自动完成下拉刷新和上拉加载
 */

fun <T> BaseByRecyclerViewAdapter<T, *>.finish(
    data: List<T>?,
    emptyLayoutId: Int = R.layout.layout_empty
) {
    if (recyclerView.isRefreshing || this.data.isNullOrEmpty()) {
        if (data.isNullOrEmpty()) {
            recyclerView.setStateView(emptyLayoutId)
            recyclerView.isLoadMoreEnabled = false
            setNewData(null)
        } else {
            recyclerView.isStateViewEnabled = false
            recyclerView.isLoadMoreEnabled = true
            setNewData(data)
        }
    } else {
        if (data.isNullOrEmpty()) {
            recyclerView.loadMoreEnd()
        } else {
            addData(data)
            recyclerView.loadMoreComplete()
        }
    }
}

fun <T> BaseByRecyclerViewAdapter<T, *>.setSkeleton(): ByRVItemSkeletonScreen? {
    return BySkeleton
        .bindItem(recyclerView)
        .adapter(this)
        .color(R.color.white)// 动画的颜色
        .show()
}