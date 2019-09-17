package iam.thevoid.common.adapter.delegate

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Message
import android.view.View
import android.webkit.*
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi

open class WebChromeClientDelegate(private val client: WebChromeClient?) : WebChromeClient() {

    @CallSuper
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        client?.onProgressChanged(view, newProgress)
    }

    @CallSuper
    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        client?.onReceivedTitle(view, title)
    }

    @CallSuper
    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        client?.onReceivedIcon(view, icon)
    }

    @CallSuper
    override fun onReceivedTouchIconUrl(view: WebView?, url: String?, precomposed: Boolean) {
        super.onReceivedTouchIconUrl(view, url, precomposed)
        client?.onReceivedTouchIconUrl(view, url, precomposed)
    }

    @CallSuper
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        client?.onShowCustomView(view, callback)
    }

    @CallSuper
    @Deprecated("Use 'onShowCustomView(view: View?, callback: CustomViewCallback?)' instead")
    override fun onShowCustomView(
        view: View?,
        requestedOrientation: Int,
        callback: CustomViewCallback?
    ) {
        super.onShowCustomView(view, requestedOrientation, callback)
        client?.onShowCustomView(view, requestedOrientation, callback)
    }

    @CallSuper
    override fun onHideCustomView() {
        super.onHideCustomView()
        client?.onHideCustomView()
    }

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean = client?.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        ?: super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)

    @CallSuper
    override fun onRequestFocus(view: WebView?) {
        super.onRequestFocus(view)
        client?.onRequestFocus(view)
    }

    @CallSuper
    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
        client?.onCloseWindow(window)
    }

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean = client?.onJsAlert(view, url, message, result)
        ?: super.onJsAlert(view, url, message, result)

    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean =
        client?.onJsConfirm(view, url, message, result)
            ?: super.onJsConfirm(view, url, message, result)

    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean = client?.onJsPrompt(view, url, message, defaultValue, result)
        ?: super.onJsPrompt(view, url, message, defaultValue, result)

    override fun onJsBeforeUnload(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean = client?.onJsBeforeUnload(view, url, message, result)
        ?: super.onJsBeforeUnload(view, url, message, result)

    @Deprecated("")
    override fun onExceededDatabaseQuota(
        url: String?,
        databaseIdentifier: String?,
        quota: Long,
        estimatedDatabaseSize: Long,
        totalQuota: Long,
        quotaUpdater: WebStorage.QuotaUpdater?
    ) {
        super.onExceededDatabaseQuota(
            url,
            databaseIdentifier,
            quota,
            estimatedDatabaseSize,
            totalQuota,
            quotaUpdater
        )
        client?.onExceededDatabaseQuota(
            url,
            databaseIdentifier,
            quota,
            estimatedDatabaseSize,
            totalQuota,
            quotaUpdater
        )
    }

    @Deprecated("")
    @CallSuper
    override fun onReachedMaxAppCacheSize(
        requiredStorage: Long,
        quota: Long,
        quotaUpdater: WebStorage.QuotaUpdater?
    ) {
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)
        client?.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)
    }

    @CallSuper
    override fun onGeolocationPermissionsShowPrompt(
        origin: String?,
        callback: GeolocationPermissions.Callback?
    ) {
        super.onGeolocationPermissionsShowPrompt(origin, callback)
        client?.onGeolocationPermissionsShowPrompt(origin, callback)
    }

    @CallSuper
    override fun onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt()
        client?.onGeolocationPermissionsHidePrompt()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    override fun onPermissionRequest(request: PermissionRequest?) {
        super.onPermissionRequest(request)
        client?.onPermissionRequest(request)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    override fun onPermissionRequestCanceled(request: PermissionRequest?) {
        super.onPermissionRequestCanceled(request)
        client?.onPermissionRequestCanceled(request)
    }

    @Deprecated("")
    override fun onJsTimeout(): Boolean = client?.onJsTimeout() ?: super.onJsTimeout()

    @Deprecated("")
    @CallSuper
    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
        super.onConsoleMessage(message, lineNumber, sourceID)
        client?.onConsoleMessage(message, lineNumber, sourceID)
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean =
        client?.onConsoleMessage(consoleMessage) ?: super.onConsoleMessage(consoleMessage)

    override fun getDefaultVideoPoster(): Bitmap? =
        client?.defaultVideoPoster ?: super.getDefaultVideoPoster()

    override fun getVideoLoadingProgressView(): View? =
        client?.videoLoadingProgressView ?: super.getVideoLoadingProgressView()

    @CallSuper
    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) {
        super.getVisitedHistory(callback)
        client?.getVisitedHistory(callback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        return client?.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            ?: super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }
}