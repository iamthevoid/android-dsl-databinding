package iam.thevoid.noxml.demo.ui.mvvm.revolut

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.core.mvvm.viewModel
import org.jetbrains.anko.recyclerview.v7.recyclerView
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.demo.ui.mvvm.revolutBindings
import iam.thevoid.noxml.coroutines.extensions.hideUntilLoaded
import iam.thevoid.noxml.coroutines.extensions.hideWhenLoaded
import iam.thevoid.noxml.coroutines.recycler.setItems
import org.jetbrains.anko.*

class RevolutFragment : BaseFragment() {

    private val vm by viewModel<RevolutViewModel>()

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            recyclerView {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                layoutManager = LinearLayoutManager(context)
                setItems(vm.data, revolutBindings(vm))
                hideUntilLoaded(vm.loading)
            }
            frameLayout {
                isClickable = true
                isFocusable = true
                backgroundColorResource = R.color.semi_transparent
                hideWhenLoaded(vm.blocking)
            }
            progressBar {
                hideWhenLoaded(vm.loading, vm.blocking)
            }.lparams(dip(48), dip(48), Gravity.CENTER)
        }

}