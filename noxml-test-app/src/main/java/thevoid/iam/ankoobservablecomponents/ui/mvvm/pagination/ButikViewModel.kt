package thevoid.iam.ankoobservablecomponents.ui.mvvm.pagination

import androidx.lifecycle.ViewModel
import iam.thevoid.recycler.PaginationLoader
import thevoid.iam.ankoobservablecomponents.data.api.ButikApi
import thevoid.iam.rx.rxdata.RxLoading
import thevoid.iam.rx.utils.loading
import thevoid.iam.rx.viewmodel.RxViewModel

class ButikViewModel : ViewModel() {

    val refreshing by lazy { RxLoading() }

    val loader by lazy {
        PaginationLoader({ it + 1 }) { page: Int ->
            ButikApi.catalog(page).loading(refreshing).map {
                PaginationLoader.Response(it.currentPage, it.items, it.isLastPage)
            }
        }
    }
}