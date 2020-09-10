package iam.thevoid.noxml.demo.ui.mvvm.revolut

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.core.mvvm.viewModel
import org.jetbrains.anko.recyclerview.v7.recyclerView
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.demo.ui.mvvm.currencyBindings
import iam.thevoid.noxml.rx2.extensions.view.hideUntilLoaded
import iam.thevoid.noxml.rx2.extensions.view.hideWhenLoaded
import iam.thevoid.noxml.rx2.recycler.extensions.setItems
import org.jetbrains.anko.*

class CurrenciesFragment : BaseFragment() {

    private val vm by viewModel<CurrencyViewModel>()

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            recyclerView {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                layoutManager = LinearLayoutManager(context)
                setItems(vm.data, currencyBindings(vm))
                hideUntilLoaded(vm.loading)
            }.lparams(matchParent, matchParent)
            frameLayout {
                isClickable = true
                isFocusable = true
                backgroundColorResource = R.color.semi_transparent
                hideWhenLoaded(vm.blocking)
            }
            progressBar {
                hideWhenLoaded(vm.blocking, vm.loading)
            }.lparams(dip(48), dip(48), Gravity.CENTER)
        }

}