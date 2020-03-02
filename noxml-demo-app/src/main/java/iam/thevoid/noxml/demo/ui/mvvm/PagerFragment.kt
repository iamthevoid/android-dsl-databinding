package iam.thevoid.noxml.demo.ui.mvvm

import android.view.View
import androidx.fragment.app.Fragment
import iam.thevoid.noxml.demo.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.demo.ui.mvvm.just.JustFragment
import iam.thevoid.noxml.rx.recycler.extensions.setUntitledFactories

class PagerFragment : BaseFragment() {

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            viewPager {
                id = R.id.pager
                setUntitledFactories(
                    listOf<() -> Fragment>(
                        { JustFragment() },
                        { JustFragment() }
                    )
                )
            }
        }

}
