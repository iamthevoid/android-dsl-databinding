package iam.thevoid.noxml.coroutines.extensions.toolbar

import android.widget.Toolbar
import androidx.annotation.RequiresApi
import iam.thevoid.ae.string
import iam.thevoid.e.safe
import iam.thevoid.noxml.coroutines.data.CoroutineCharSequence
import iam.thevoid.noxml.coroutines.data.CoroutineField
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.coroutines.data.CoroutineString
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.setTitle(title : CoroutineString) =
    setTitle(title.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setTitle(title : CoroutineField<T>) =
    setTitle(title.observe().map { it.item.safe() })

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setTitle(title : CoroutineItem<T>) =
    setTitle(title.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setTitle(title : CoroutineCharSequence<T>) =
    setTitle(title.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setTitle(title : Flow<T>) =
    addSetter(title) { this.title = it }

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.setTitleResource(title : Flow<Int>) =
    addSetter(title) { this.title = string(it) }


@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.setSubtitle(subtitle : CoroutineString) =
    setSubtitle(subtitle.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setSubtitle(subtitle : CoroutineField<T>) =
    setSubtitle(subtitle.observe().map { it.item.safe() })

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setSubtitle(subtitle : CoroutineItem<T>) =
    setSubtitle(subtitle.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setSubtitle(subtitle : CoroutineCharSequence<T>) =
    setSubtitle(subtitle.observe())

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun <T : CharSequence> Toolbar.setSubtitle(subtitle : Flow<T>) =
    addSetter(subtitle) { this.subtitle = it }

@RequiresApi(android.os.Build.VERSION_CODES.LOLLIPOP)
fun Toolbar.setSubtitleResource(subtitle : Flow<Int>) =
    addSetter(subtitle) { this.subtitle = string(it) }