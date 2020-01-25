package thevoid.iam.ankoobservablecomponents.ui.mvvm.pagination

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import iam.thevoid.ankorx.AnkoLayout
import iam.thevoid.common.viewModel
import iam.thevoid.recycler.resetEndlessScrollState
import iam.thevoid.recycler.setItems
import iam.thevoid.recycler.setLoadMore
import iam.thevoid.rxe.mapSelf
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import thevoid.iam.ankoobservablecomponents.data.api.butik.Product
import thevoid.iam.ankoobservablecomponents.ui.BaseFragment
import thevoid.iam.rx.adapter.ItemBindings
import thevoid.iam.rx.widget.ext.setRefreshing
import thevoid.iam.rx.widget.ext.setText

class PaginationLoaderFragment : BaseFragment() {

    val vm by viewModel<ButikViewModel>()

    val bindings by lazy { ItemBindings.of(Product::class) { ProductLayout(it) } }

    override fun createView(ui: AnkoContext<BaseFragment>): View =
        ui.swipeRefreshLayout {
            setRefreshing(vm.refreshing)
            recyclerView {
                layoutManager = LinearLayoutManager(context)
                setItems(vm.loader.items, bindings)
                setLoadMore(vm.loader::loadMore)
            }.also {
                setOnRefreshListener { vm.loader.refresh() }
                it.resetEndlessScrollState()
            }
        }

    class ProductLayout(parent: ViewGroup) : AnkoLayout<Product>(parent) {
        override fun createView(ui: AnkoContext<AnkoLayout<Product>>): View =
            ui.verticalLayout {
                textView {
                    padding = dip(6)
                    setText(itemChanges.mapSelf { "$name $brand" })
                }
                frameLayout {
                    backgroundColorResource = android.R.color.black
                }.lparams(matchParent, dip(1)) {
                    bottomMargin = dip(32)
                }
            }.apply { layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent) }
    }
}