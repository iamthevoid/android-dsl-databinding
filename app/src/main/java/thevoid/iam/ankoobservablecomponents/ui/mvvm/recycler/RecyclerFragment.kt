package thevoid.iam.ankoobservablecomponents.ui.mvvm.recycler

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import iam.thevoid.common.ViewModelBindingProvider
import iam.thevoid.common.createBinding
import iam.thevoid.recycler.setItems

class RecyclerFragment : AnkoMvvmFragment<RecyclerViewModel>() {

    override fun provideViewModel(): iam.thevoid.common.ViewModelBindingProvider = createBinding(RecyclerViewModel::class)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<RecyclerViewModel>>): View =
        ui.frameLayout {
            recyclerView {
                (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
                layoutManager = LinearLayoutManager(context)
                setItems(vm.data, vm.itemBindings, vm.diffCallback)
            }
        }

}