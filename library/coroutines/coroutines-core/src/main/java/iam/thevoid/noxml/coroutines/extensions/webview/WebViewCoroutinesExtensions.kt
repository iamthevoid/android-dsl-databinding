package iam.thevoid.noxml.coroutines.extensions.webview

import android.webkit.WebView
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import iam.thevoid.noxml.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.extensions.webview.chromeClient
import kotlinx.coroutines.flow.Flow

fun <T : CharSequence> WebView.setHtml(
    html: Flow<T>,
    baseUrl: String = "",
    mimeType: String = "text/html",
    encoding: String = "utf-8",
    historyUrl: String? = null
) =
    addSetter(html) {
        loadDataWithBaseURL(
            baseUrl,
            it.toString(),
            mimeType,
            encoding,
            historyUrl
        )
    }

fun WebView.onLoading(onLoading: CoroutineBoolean) {
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