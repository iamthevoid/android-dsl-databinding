package iam.thevoid.noxml.demo.ui.mvvm.scroll

import android.graphics.Color
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import iam.thevoid.ae.screenHeight
import iam.thevoid.ae.setPaddings
import iam.thevoid.noxml.core.mvvm.viewModel
import iam.thevoid.e.safe
import iam.thevoid.noxml.rx2.recycler.extensions.setItems
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.viewPager
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.rx2.extensions.setItems
import kotlin.math.absoluteValue

class PagerCoordinatorFragment : BaseFragment(), AppBarLayout.OnOffsetChangedListener {

    lateinit var toolbar: Toolbar

    var treshhold = 0f

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        if (treshhold == 0f)
            treshhold = appBarLayout?.totalScrollRange.safe().toFloat() * 2 / 3

        toolbar.alpha =
            (verticalOffset.toFloat().absoluteValue - treshhold) / (appBarLayout?.totalScrollRange.safe().toFloat() - treshhold)
    }

    val vm by viewModel<ScrollViewModel>()

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.coordinatorLayout {

            appBarLayout {
                addOnOffsetChangedListener(this@PagerCoordinatorFragment)
                collapsingToolbarLayout {
                    viewPager {
                        clipToPadding = false
                        setPaddings(left = dip(64), right = dip(64))
                        pageMargin = dip(32)
                        setItems(vm.images, vm.pagerBindings)
                    }.lparams(width = matchParent, height = screenHeight * 2 / 3) {
                        collapseMode = COLLAPSE_MODE_PARALLAX
                        topMargin = dip(16)
                        bottomMargin = dip(16)
                    }

                    toolbar = toolbar {
                        alpha = 0f
                        backgroundColor = Color.WHITE
                        frameLayout {
                            textView {
                                textColor = Color.BLACK
                                textSizeDimen = R.dimen.text_extra_large
                                text = "I am invisible"
                            }
                        }
                    }.lparams(matchParent, wrapContent) {
                        collapseMode = COLLAPSE_MODE_PIN
                    }
                }.lparams(width = matchParent) {
                    scrollFlags = SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP
                }
            }.lparams(width = matchParent)

            recyclerView {
                layoutManager = LinearLayoutManager(context)
                setItems(vm.items, vm.binding)
            }.lparams(matchParent, matchParent) {
                behavior = OversizeBehavior()
            }
        }


}