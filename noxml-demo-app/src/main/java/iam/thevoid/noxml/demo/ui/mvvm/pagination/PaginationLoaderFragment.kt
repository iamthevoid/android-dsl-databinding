package iam.thevoid.noxml.demo.ui.mvvm.pagination

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import iam.thevoid.noxml.core.mvvm.viewModel
import iam.thevoid.noxml.rx.recycler.extensions.setItems
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import iam.thevoid.noxml.demo.data.api.butik.Product
import iam.thevoid.noxml.demo.ui.BaseFragment
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.recycler.resetEndlessScrollState
import iam.thevoid.noxml.recycler.setLoadMore
import iam.thevoid.rxe.mapSelf
import iam.thevoid.noxml.rx.extensions.setText
import iam.thevoid.noxml.rx.swiperefreshlayout.setRefreshing

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

    class ProductLayout(parent: ViewGroup) : AnkoRxLayout<Product>(parent) {
        override fun createView(ui: AnkoContext<AnkoRxLayout<Product>>): View =
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