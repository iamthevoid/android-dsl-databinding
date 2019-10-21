package thevoid.iam.ankoobservablecomponents.ui.mvvm.serial

import iam.thevoid.recycler.PaginationLoader
import io.reactivex.Single
import thevoid.iam.ankoobservablecomponents.data.api.ButikApi
import thevoid.iam.ankoobservablecomponents.data.api.butik.Product
import thevoid.iam.rx.rxdata.RxLoading
import thevoid.iam.rx.utils.loading
import thevoid.iam.rx.viewmodel.RxViewModel

class ButikViewModel : RxViewModel() {

    val refreshing by lazy { RxLoading() }

    val productPage: ((Int) -> Single<PaginationLoader.Response<Product>>) by lazy {
        { page: Int ->
            ButikApi.catalog(page + 1).loading(refreshing).map {
                PaginationLoader.Response(it.items, it.isLastPage)
            }
        }
    }

}