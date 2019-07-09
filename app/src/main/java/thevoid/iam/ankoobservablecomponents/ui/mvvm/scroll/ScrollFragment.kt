package thevoid.iam.ankoobservablecomponents.ui.mvvm.scroll

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import iam.thevoid.ankoviews.widget.mvvm.AnkoMvvmFragment
import iam.thevoid.recycler.onRecyclerScrolled
import iam.thevoid.recycler.setItems
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import thevoid.iam.components.mvvm.ViewModelBindingProvider
import thevoid.iam.components.mvvm.createBinding

class ScrollFragment : AnkoMvvmFragment<ScrollViewModel>() {
    override fun provideViewModel(): ViewModelBindingProvider = createBinding(ScrollViewModel::class.java)

    override fun createView(ui: AnkoContext<AnkoMvvmFragment<ScrollViewModel>>): View =
        ui.recyclerView {
            layoutManager = LinearLayoutManager(context)
            setItems(vm.items, vm.binding)
            onRecyclerScrolled(vm.scrolled) { it.dy }
        }
}