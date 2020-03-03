package iam.thevoid.noxml.rx.swiperefreshlayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import iam.thevoid.noxml.rx.extensions.addSetter
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.data.RxLoading
import iam.thevoid.noxml.rx.data.fields.RxBoolean
import io.reactivex.rxkotlin.Flowables

fun SwipeRefreshLayout.setRefreshing(refreshing : Flowable<Boolean>) =
        addSetter(refreshing) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(refreshing : RxLoading) =
        setRefreshing(refreshing.observe())

fun SwipeRefreshLayout.setRefreshing(refreshing : RxBoolean) =
        setRefreshing(refreshing.observe())

fun SwipeRefreshLayout.setRefreshing(refreshing1: RxLoading, refreshing2: RxLoading) =
        setRefreshing(Flowables.combineLatest(refreshing1.observe(), refreshing2.observe()).map { (f, s) -> f || s })