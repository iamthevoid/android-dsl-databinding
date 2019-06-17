package thevoid.iam.ankoobservablecomponents.recycler

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import thevoid.iam.components.mvvm.ViewModelBundleProvider
import thevoid.iam.components.mvvm.createBundle
import thevoid.iam.components.widget.ext.setItems

class RecyclerFragment : AnkoMvvmFragment<RecyclerViewModel>() {
    override fun provideViewModel(): ViewModelBundleProvider = createBundle(RecyclerViewModel::class)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<RecyclerViewModel>>): View =
        ui.frameLayout {
            recyclerView {
                layoutManager = LinearLayoutManager(context)
                setItems(vm.data, vm.itemBindings, vm.diffCallback)
            }
        }

}