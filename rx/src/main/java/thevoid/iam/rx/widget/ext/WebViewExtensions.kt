package thevoid.iam.rx.widget.ext

import android.webkit.WebView
import io.reactivex.Flowable


fun <T : CharSequence> WebView.setHtml(htmlFlowable: Flowable<T>, baseUrl : String = "", mimeType : String = "text/html", encoding : String = "utf-8", historyUrl : String? = null) =
    addSetter(htmlFlowable) { loadDataWithBaseURL(baseUrl, it.toString(), mimeType, encoding, historyUrl) }