package iam.thevoid.noxml.rx.extensions

import android.webkit.WebView
import iam.thevoid.noxml.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.extensions.chromeClient
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.rxdata.RxLoading


fun <T : CharSequence> WebView.setHtml(
    htmlFlowable: Flowable<T>,
    baseUrl: String = "",
    mimeType: String = "text/html",
    encoding: String = "utf-8",
    historyUrl: String? = null
) =
    addSetter(htmlFlowable) {
        loadDataWithBaseURL(
            baseUrl,
            it.toString(),
            mimeType,
            encoding,
            historyUrl
        )
    }

fun WebView.onLoading(loading: RxLoading) {
    chromeClient = object : WebChromeClientDelegate(chromeClient) {

        var loadingProgress = 0

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (loadingProgress == 0) {
                loading.inc()
            }

            if (newProgress == 100) {
                loading.dec()
            }

            // reset progress on finish
            loadingProgress = (if (loadingProgress < 100) newProgress else 0)
        }
    }
}