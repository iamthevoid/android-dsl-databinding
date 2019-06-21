package thevoid.iam.ankoobservablecomponents.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.ui.mvvm.fragment.JustFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.fragment.PageFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.fragment.RecyclerFragment

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
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> RecyclerFragment()
            1 -> PageFragment()
            else -> JustFragment()
        }
        override fun getCount(): Int = 4
    }
}
