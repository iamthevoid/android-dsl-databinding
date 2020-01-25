package thevoid.iam.ankoobservablecomponents.ui.mvvm

import android.view.View
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.support.v4.viewPager
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.ankoobservablecomponents.ui.BaseFragment
import thevoid.iam.ankoobservablecomponents.ui.mvvm.just.JustFragment
import thevoid.iam.rx.widget.ext.setUntitledFactories

class PagerFragment : BaseFragment() {

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            viewPager {
                id = R.id.pager
                setUntitledFactories(
                    listOf(
                        { JustFragment() },
                        { JustFragment() }
                    )
                )
            }
        }

}
