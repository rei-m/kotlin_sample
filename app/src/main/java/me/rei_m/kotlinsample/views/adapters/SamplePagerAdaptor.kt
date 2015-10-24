package me.rei_m.kotlinsample.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import me.rei_m.kotlinsample.fragments.PagerSampleFragment

class SamplePagerAdaptor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return PagerSampleFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "${position + 1} 枚目"
    }
}