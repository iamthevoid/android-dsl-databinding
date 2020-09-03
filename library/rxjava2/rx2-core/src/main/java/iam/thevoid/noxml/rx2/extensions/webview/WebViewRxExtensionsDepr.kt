package iam.thevoid.noxml.rx2.extensions.webview

import android.webkit.WebView
import iam.thevoid.noxml.core.local.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.core.local.extensions.webview.chromeClient
import iam.thevoid.noxml.rx2.data.fields.RxBoolean

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
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