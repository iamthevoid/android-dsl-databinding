package iam.thevoid.noxml.rx2.extensions.webview

import android.webkit.WebView
import iam.thevoid.noxml.delegate.WebChromeClientDelegate
import iam.thevoid.noxml.extensions.webview.chromeClient
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxBoolean
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.BackpressureStrategy
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor


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

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
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