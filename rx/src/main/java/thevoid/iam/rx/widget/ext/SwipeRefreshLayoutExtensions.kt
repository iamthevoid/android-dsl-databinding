package thevoid.iam.rx.widget.ext

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Flowable
import thevoid.iam.rx.rxdata.RxLoading
import thevoid.iam.rx.rxdata.fields.RxBoolean


fun SwipeRefreshLayout.setRefreshing(loading : RxLoading) =
        addSetter(loading.asFlowable) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading : RxBoolean) =
        addSetter(loading.observe()) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading : Flowable<Boolean>) =
        addSetter(loading) { isRefreshing = it }