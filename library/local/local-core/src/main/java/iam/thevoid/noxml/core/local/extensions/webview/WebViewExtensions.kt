package iam.thevoid.noxml.core.local.extensions.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import iam.thevoid.noxml.core.local.R

// This delegate created because original WebChromeClient getter of WebView only available from API 26
var WebView.chromeClient
    get() = (getTag(R.id.chromeClient) as? WebChromeClient)
    set(client) {
        setTag(R.id.chromeClient, client)
        webChromeClient = client
    }
