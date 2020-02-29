@file:UseExperimental(ExperimentalCoroutinesApi::class)

package iam.thevoid.noxml.coroutines.swiperefreshlayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import iam.thevoid.noxml.coroutines.extensions.addSetter
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


fun SwipeRefreshLayout.setRefreshing(loading: CoroutineBoolean) =
    addSetter(loading.observe()) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(loading1: CoroutineBoolean, loading2: CoroutineBoolean) =
    addSetter(combine(loading1.observe(), loading2.observe()) { f, s -> Pair(f, s) }) { (f, s) ->
        isRefreshing = f || s
    }

fun SwipeRefreshLayout.setRefreshing(loading: Flow<Boolean>) =
    addSetter(loading) { isRefreshing = it }