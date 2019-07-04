package thevoid.iam.ankoobservablecomponents.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.ui.mvvm.just.JustFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.page.PageFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler.RecyclerFragment
import thevoid.iam.components.widget.ext.setUntitledFactories
import thevoid.iam.components.widget.ext.setUntitledFragments

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            viewPager {
                id = R.id.pager
                setUntitledFactories(
                    listOf(
                        { PageFragment() },
                        { RecyclerFragment() },
                        { JustFragment() },
                        { JustFragment() }
                    )
                )
            }
        }
    }
}
