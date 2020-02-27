package iam.thevoid.noxml.rx.ext

import android.webkit.WebChromeClient
import android.webkit.WebView
import iam.thevoid.noxml.rx.R
import iam.thevoid.noxml.delegate.WebChromeClientDelegate
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.rxdata.RxLoading

// This delegate created because original WebChromeClient getter of WebView only available from API 26
var WebView.chromeClient
    get() = (getTag(R.id.chromeClient) as? WebChromeClient)
    set(client) {
        setTag(R.id.chromeClient, client)
        webChromeClient = client
    }

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

fun WebView.onLoading(rxLoading: RxLoading) {
    chromeClient = object : WebChromeClientDelegate(chromeClient) {

        var loadingProgress = 0

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (loadingProgress == 0) {
                rxLoading.inc()
            }

            if (newProgress == 100) {
                rxLoading.dec()
            }

            // reset progress on finish
            loadingProgress = (if (loadingProgress < 100) newProgress else 0)
        }
    }
}