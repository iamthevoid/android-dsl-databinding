package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import iam.thevoid.common.viewModel
import iam.thevoid.recycler.setItems
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import thevoid.iam.ankoobservablecomponents.ui.BaseFragment

class RecyclerFragment : BaseFragment() {

    val vm by viewModel<RecyclerViewModel>()

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.frameLayout {
            recyclerView {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                layoutManager = LinearLayoutManager(context)
                setItems(vm.data, vm.itemBindings)
            }
        }

}