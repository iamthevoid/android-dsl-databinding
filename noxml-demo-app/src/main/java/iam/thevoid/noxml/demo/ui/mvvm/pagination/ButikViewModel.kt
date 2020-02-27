package iam.thevoid.noxml.demo.ui.mvvm.pagination

import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.extensions.PaginationLoader
import iam.thevoid.noxml.demo.data.api.ButikApi
import iam.thevoid.noxml.rx.rxdata.RxLoading
import iam.thevoid.noxml.rx.utils.loading

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