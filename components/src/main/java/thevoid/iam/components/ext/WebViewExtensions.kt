package thevoid.iam.components.ext

import android.webkit.WebView
import io.reactivex.Flowable


fun <T : CharSequence> WebView.setHtml(htmlFlowable: Flowable<T>, baseUrl : String = "", mimeType : String = "text/html", encoding : String = "UTF-8", historyUrl : String? = null) =
    addSetter(htmlFlowable) { loadDataWithBaseURL(baseUrl, it.toString(), mimeType, encoding, null) }