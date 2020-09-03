package iam.thevoid.noxml.rx2.extensions.webview

import android.webkit.WebView
import iam.thevoid.noxml.core.local.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.core.local.extensions.webview.chromeClient
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxBoolean
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.BackpressureStrategy
import io.reactivex.processors.FlowableProcessor


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

fun WebView.onLoading(onLoading: FlowableProcessor<Boolean>) {
    addSetter(Flowable.create<Boolean>({
        val oldChromeClient = chromeClient
        chromeClient = object : WebChromeClientDelegate(oldChromeClient) {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100) {
                    it.onNext(true)
                }

                if (newProgress == 100) {
                    it.onNext(false)
                }
            }
        }
        it.setCancellable { chromeClient = oldChromeClient }
    }, BackpressureStrategy.LATEST).doOnNext(onLoading::onNext))
}