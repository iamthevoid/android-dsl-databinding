package iam.thevoid.noxml.rx.swiperefreshlayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import iam.thevoid.noxml.rx.extensions.addSetter
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.rxdata.RxLoading
import iam.thevoid.noxml.rx.rxdata.fields.RxBoolean
import io.reactivex.rxkotlin.Flowables


fun SwipeRefreshLayout.setRefreshing(loading : RxLoading) =
        addSetter(loading.observe()) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading1: RxLoading, loading2: RxLoading) =
        addSetter(Flowables.combineLatest(loading1.observe(), loading2.observe())) { (f, s) ->
                isRefreshing = f || s
        }

fun SwipeRefreshLayout.setRefreshing(loading : RxBoolean) =
        addSetter(loading.observe()) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading : Flowable<Boolean>) =
        addSetter(loading) { isRefreshing = it }