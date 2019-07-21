package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import com.google.android.material.appbar.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import iam.thevoid.ae.screenHeight
import iam.thevoid.ae.setPaddings
import iam.thevoid.ae.statusBarHeight
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmActivity
import iam.thevoid.e.safe
import iam.thevoid.recycler.setItems
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.viewPager
import thevoid.iam.ankoobservablecomponents.R
import thevoid.iam.components.mvvm.ViewModelBindingProvider
import thevoid.iam.components.mvvm.createBinding
import thevoid.iam.components.widget.ext.setItems
import kotlin.math.absoluteValue

class ScrollActivity : AnkoMvvmActivity<ScrollViewModel>(), AppBarLayout.OnOffsetChangedListener {

    lateinit var toolbar: Toolbar

    var treshhold = 0f

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        if (treshhold == 0f)
            treshhold = appBarLayout?.totalScrollRange.safe().toFloat() * 2 / 3

        toolbar.alpha =
            (verticalOffset.toFloat().absoluteValue - treshhold) / (appBarLayout?.totalScrollRange.safe().toFloat() - treshhold)
    }

    override fun provideViewModel(): ViewModelBindingProvider = createBinding(ScrollViewModel::class.java)

    override fun createView(ui: AnkoContext<Context>): View =
        ui.coordinatorLayout {

            appBarLayout {
                addOnOffsetChangedListener(this@ScrollActivity)
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