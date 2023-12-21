package com.renhuan.utils.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class MyFragmentPagerAdapter<T : Fragment>(
    fm: FragmentManager,
    private var mFragmentList: ArrayList<T>,
    private var mTitleList: ArrayList<String>? = null
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitleList == null) {
            super.getPageTitle(position)
        } else {
            mTitleList?.let { it[position] }
        }
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

}