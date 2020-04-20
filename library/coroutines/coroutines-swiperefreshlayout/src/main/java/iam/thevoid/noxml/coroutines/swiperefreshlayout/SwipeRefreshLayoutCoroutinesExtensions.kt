@file:UseExperimental(ExperimentalCoroutinesApi::class)

package iam.thevoid.noxml.coroutines.swiperefreshlayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.utils.Coroutines
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun SwipeRefreshLayout.setRefreshing(refreshing: Flow<Boolean>) =
    addSetter(refreshing) { isRefreshing = it }

fun SwipeRefreshLayout.setRefreshing(refreshing: CoroutineBoolean) =
    setRefreshing(refreshing.observe())

fun SwipeRefreshLayout.setRefreshing(refreshing1: CoroutineBoolean, refreshing2: CoroutineBoolean) =
    setRefreshing(Coroutines.combine(refreshing1.observe(), refreshing2.observe()).map { (f,s) -> f || s })