package iam.thevoid.noxml.rx2.extensions.webview

import android.webkit.WebView
import iam.thevoid.noxml.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.extensions.webview.chromeClient
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxBoolean
import iam.thevoid.noxml.rx2.extensions.view.addSetter


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

fun WebView.onLoading(onLoading: RxBoolean) {
    chromeClient = object : WebChromeClientDelegate(chromeClient) {

        var loadingProgress = 0

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (loadingProgress <= 100 && !onLoading.get()) {
                onLoading.set(true)
            }

            if (newProgress == 100) {
                onLoading.set(false)
            }

            // reset progress on finish
            loadingProgress = (if (loadingProgress < 100) newProgress else 0)
        }
    }
}