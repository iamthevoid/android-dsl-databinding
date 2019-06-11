package thevoid.iam.components.widget.ext

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Flowable
import thevoid.iam.components.rx.RxLoading
import thevoid.iam.components.rx.fields.RxBoolean


fun SwipeRefreshLayout.setRefreshing(loading : RxLoading) =
        addSetter(loading.asFlowable) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading : RxBoolean) =
        addSetter(loading.observe()) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading : Flowable<Boolean>) =
        addSetter(loading) { isRefreshing = it }