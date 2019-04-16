package com.example.ankoobservablecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            viewPager {
                id = R.id.pager
                adapter = Adapter(supportFragmentManager)
            }
        }
    }

    class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = if (position == 0) PageFragment() else JustFragment()
        override fun getCount(): Int = 10
    }
}
