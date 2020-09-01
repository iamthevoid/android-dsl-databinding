package iam.thevoid.noxml.core.local.delegate

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Message
import android.view.View
import android.webkit.*
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi

@Suppress("DEPRECATION")
open class WebChromeClientDelegate(private val client: WebChromeClient?) : WebChromeClient() {

    @CallSuper
    override fun onProgressChanged(view: WebView?, newProgress: Int) =
        client?.onProgressChanged(view, newProgress) ?: super.onProgressChanged(view, newProgress)

    @CallSuper
    override fun onReceivedTitle(view: WebView?, title: String?) =
        client?.onReceivedTitle(view, title) ?: super.onReceivedTitle(view, title)

    @CallSuper
    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) =
        client?.onReceivedIcon(view, icon) ?: super.onReceivedIcon(view, icon)

    @CallSuper
    override fun onReceivedTouchIconUrl(view: WebView?, url: String?, precomposed: Boolean) =
        client?.onReceivedTouchIconUrl(view, url, precomposed)
            ?: super.onReceivedTouchIconUrl(view, url, precomposed)

    @CallSuper
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) =
        client?.onShowCustomView(view, callback) ?: super.onShowCustomView(view, callback)

    @Deprecated("Use 'onShowCustomView(view: View?, callback: CustomViewCallback?)' instead")
    @CallSuper
    override fun onShowCustomView(
        view: View?,
        requestedOrientation: Int,
        callback: CustomViewCallback?
    ) =
        client?.onShowCustomView(view, requestedOrientation, callback)
            ?: super.onShowCustomView(view, requestedOrientation, callback)

    @CallSuper
    override fun onHideCustomView() = client?.onHideCustomView() ?: super.onHideCustomView()

    @CallSuper
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean = client?.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        ?: super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)

    @CallSuper
    override fun onRequestFocus(view: WebView?) =
        client?.onRequestFocus(view) ?: super.onRequestFocus(view)

    @CallSuper
    override fun onCloseWindow(window: WebView?) =
        client?.onCloseWindow(window) ?: super.onCloseWindow(window)

    @CallSuper
    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean = client?.onJsAlert(view, url, message, result)
        ?: super.onJsAlert(view, url, message, result)

    @CallSuper
    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean = client?.onJsConfirm(view, url, message, result)
        ?: super.onJsConfirm(view, url, message, result)

    @CallSuper
    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean = client?.onJsPrompt(view, url, message, defaultValue, result)
        ?: super.onJsPrompt(view, url, message, defaultValue, result)

    @CallSuper
    override fun onJsBeforeUnload(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean = client?.onJsBeforeUnload(view, url, message, result)
        ?: super.onJsBeforeUnload(view, url, message, result)

    @Deprecated("")
    @CallSuper
    override fun onExceededDatabaseQuota(
        url: String?,
        databaseIdentifier: String?,
        quota: Long,
        estimatedDatabaseSize: Long,
        totalQuota: Long,
        quotaUpdater: WebStorage.QuotaUpdater?
    ) {
        client?.onExceededDatabaseQuota(
            url,
            databaseIdentifier,
            quota,
            estimatedDatabaseSize,
            totalQuota,
            quotaUpdater
        )
            ?: super.onExceededDatabaseQuota(
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
    ) =
        client?.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)
            ?: super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)

    @CallSuper
    override fun onGeolocationPermissionsShowPrompt(
        origin: String?,
        callback: GeolocationPermissions.Callback?
    ) =
        client?.onGeolocationPermissionsShowPrompt(origin, callback)
            ?: super.onGeolocationPermissionsShowPrompt(origin, callback)

    @CallSuper
    override fun onGeolocationPermissionsHidePrompt() =
        client?.onGeolocationPermissionsHidePrompt() ?: super.onGeolocationPermissionsHidePrompt()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    override fun onPermissionRequest(request: PermissionRequest?) {
        this.client?.onPermissionRequest(request) ?: super.onPermissionRequest(request)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    override fun onPermissionRequestCanceled(request: PermissionRequest?) =
        client?.onPermissionRequestCanceled(request) ?: super.onPermissionRequestCanceled(request)

    @Deprecated("")
    @CallSuper
    override fun onJsTimeout(): Boolean = client?.onJsTimeout() ?: super.onJsTimeout()

    @Deprecated("")
    @CallSuper
    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) =
        client?.onConsoleMessage(message, lineNumber, sourceID)
            ?: super.onConsoleMessage(message, lineNumber, sourceID)

    @CallSuper
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean =
        client?.onConsoleMessage(consoleMessage) ?: super.onConsoleMessage(consoleMessage)

    @CallSuper
    override fun getDefaultVideoPoster(): Bitmap? =
        client?.defaultVideoPoster ?: super.getDefaultVideoPoster()

    @CallSuper
    override fun getVideoLoadingProgressView(): View? =
        client?.videoLoadingProgressView ?: super.getVideoLoadingProgressView()

    @CallSuper
    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) =
        client?.getVisitedHistory(callback) ?: super.getVisitedHistory(callback)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean = client?.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        ?: super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
}