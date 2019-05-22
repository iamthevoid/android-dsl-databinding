package thevoid.iam.components.ext

import android.webkit.WebView
import io.reactivex.Flowable


fun <T : CharSequence> WebView.setHtml(htmlFlowable: Flowable<T>) =
    addSetter(htmlFlowable) { loadDataWithBaseURL("", it.toString(), "text/html", "UTF-8", "") }