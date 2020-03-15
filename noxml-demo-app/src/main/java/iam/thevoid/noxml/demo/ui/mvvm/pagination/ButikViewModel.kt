package iam.thevoid.noxml.demo.ui.mvvm.pagination

import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.rx2.recycler.pagination.PaginationLoader
import iam.thevoid.noxml.demo.data.api.ButikApi
import iam.thevoid.noxml.rx2.data.RxLoading
import iam.thevoid.noxml.rx2.utils.loading

class ButikViewModel : ViewModel() {

    val refreshing by lazy { RxLoading() }

    val loader by lazy {
        PaginationLoader({ it + 1 }) { page: Int ->
            ButikApi.catalog(page).loading(refreshing).map {
                PaginationLoader.Response(
                    it.currentPage,
                    it.items,
                    it.isLastPage
                )
            }
        }
    }
}