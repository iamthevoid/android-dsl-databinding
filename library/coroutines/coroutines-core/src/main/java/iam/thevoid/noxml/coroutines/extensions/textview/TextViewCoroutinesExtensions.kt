package iam.thevoid.noxml.coroutines.extensions.textview

import android.text.Editable
import android.widget.TextView
import iam.thevoid.ae.color
import iam.thevoid.ae.setTextStrikeThru
import iam.thevoid.ae.string
import iam.thevoid.e.format
import iam.thevoid.e.safe
import iam.thevoid.noxml.core.local.adapters.TextWatcherAdapter
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.core.local.extensions.textview.hasTextWatcherDelegate
import iam.thevoid.noxml.core.local.extensions.textview.setTextSilent
import iam.thevoid.noxml.core.local.extensions.textview.textWatcherDelegate
import iam.thevoid.noxml.coroutines.data.*
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T : CharSequence> TextView.setText(text: Flow<T>) =
    addSetter(text) {
        if (hasTextWatcherDelegate()) {
            setTextSilent(it)
        } else {
            this.text = it
        }
    }

fun <T : CharSequence> TextView.setText(text: CoroutineCharSequence<T>) =
    setText(text.observe())

fun TextView.setText(text: CoroutineString) =
    setText(text.observe())

fun TextView.setText(text: CoroutineInt) =
    setText(text.observe().map { "$it" })

fun TextView.setText(text: CoroutineLong) =
    setText(text.observe().map { "$it" })

fun TextView.setText(text: CoroutineFloat, precision: Int? = null) =
    setText(text.observe().map { it.format(precision) })

fun TextView.setText(double: CoroutineDouble, precision: Int? = null) =
    setText(double.observe().map { it.format(precision) })

fun TextView.setTextResource(textResource: Flow<Int>) =
    addSetter(textResource) { text = string(it) }

fun TextView.setTextColor(color: Flow<Int>) =
    addSetter(color) { setTextColor(it) }

fun TextView.setTextColorResource(colorResource: Flow<Int>) =
    addSetter(colorResource) { setTextColor(color(it)) }

fun TextView.setHintTextColor(hintColorResource: Flow<Int>) =
    addSetter(hintColorResource) { setHintTextColor(it) }

fun TextView.setHintTextColorResource(hintColorResource: Flow<Int>) =
    addSetter(hintColorResource) { setHintTextColor(color(it)) }

fun TextView.setTextStrikeThru(strikeThru: Flow<Boolean>) =
    addSetter(strikeThru) { setTextStrikeThru(it) }

/**
 * GETTERS
 */


fun <T : Any> TextView.afterTextChanges(afterTextChanges: CoroutineField<T>, mapper: (Editable?) -> T) =
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanges.set(mapper(s))
        }
    })

fun <T : Any> TextView.afterTextChanges(afterTextChanges: CoroutineItem<T>, mapper: (Editable?) -> T) =
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanges.set(mapper(s))
        }
    })

fun TextView.afterTextChanges(afterTextChanges: CoroutineItem<String>) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }

fun TextView.afterTextChanges(afterTextChanges: CoroutineString) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }


fun <T : Any> TextView.beforeTextChanges(
    beforeTextChanges: CoroutineField<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    textWatcherDelegate.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanges.set(mapper(BeforeEditTextChanges(s, start, count, after)))
        }
    })
}

fun TextView.beforeTextChanges(beforeTextChanges: CoroutineField<BeforeEditTextChanges>) =
    beforeTextChanges(beforeTextChanges) { it }


fun <T : Any> TextView.onTextChanges(
    beforeTextChanges: CoroutineField<T>,
    mapper: (OnEditTextChanges) -> T
) {
    textWatcherDelegate.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            beforeTextChanges.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}

fun TextView.onTextChanges(beforeTextChanges: CoroutineField<OnEditTextChanges>) =
    onTextChanges(beforeTextChanges) { it }