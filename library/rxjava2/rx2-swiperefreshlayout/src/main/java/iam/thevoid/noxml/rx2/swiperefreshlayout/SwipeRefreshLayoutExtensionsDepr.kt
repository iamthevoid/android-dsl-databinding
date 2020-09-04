package iam.thevoid.noxml.rx2.swiperefreshlayout

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import iam.thevoid.noxml.rx2.data.fields.RxBoolean

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun SwipeRefreshLayout.setRefreshing(refreshing : RxBoolean) =
    setRefreshing(refreshing.observe())