package thevoid.iam.ankoobservablecomponents.ui.mvvm.serial

import iam.thevoid.recycler.NewPaginationLoader
import thevoid.iam.ankoobservablecomponents.data.api.ButikApi
import thevoid.iam.rx.rxdata.RxLoading
import thevoid.iam.rx.utils.loading
import thevoid.iam.rx.viewmodel.RxViewModel

class ButikViewModel : RxViewModel() {

    val refreshing by lazy { RxLoading() }

    val loader by lazy {
        NewPaginationLoader({ it + 1 }) { page: Int ->
            ButikApi.catalog(page).loading(refreshing).map {
                NewPaginationLoader.Response(it.currentPage, it.items, it.isLastPage)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loader.release()
    }
}