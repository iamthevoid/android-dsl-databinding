package iam.thevoid.noxml.rx2.extensions.textview

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import iam.thevoid.ae.color
import iam.thevoid.ae.setTextStrikeThru
import iam.thevoid.ae.string
import iam.thevoid.e.format
import iam.thevoid.e.safe
import iam.thevoid.noxml.adapters.TextWatcherAdapter
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.extensions.textview.textWatcher
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.*
import iam.thevoid.noxml.rx2.extensions.view.addSetter

fun <T : CharSequence> TextView.setText(text: Flowable<T>) =
    addSetter(text) { this.text = it }

fun <T : CharSequence> TextView.setText(text: RxCharSequence<T>) =
    setText(text.observe())

fun TextView.setText(text: RxString) =
    setText(text.observe())

fun TextView.setText(text: RxInt) =
    setText(text.observe().map { "$it" })

fun TextView.setText(text: RxLong) =
    addSetter(text.observe().map { "$it" })

fun TextView.setText(text: RxFloat, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

fun TextView.setText(text: RxDouble, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

fun TextView.setTextResource(textResource: Flowable<Int>) =
    setText(textResource.map { string(it) })

fun TextView.setTextResource(textResource: RxInt) =
    setTextResource(textResource.observe())

fun TextView.setTextColor(color: Flowable<Int>) =
    addSetter(color) { setTextColor(it) }

fun TextView.setTextColorResource(colorResource: Flowable<Int>) =
    addSetter(colorResource) { setTextColor(color(it)) }

fun TextView.setHintTextColor(color: Flowable<Int>) =
    addSetter(color) { setHintTextColor(it) }

fun TextView.setHintTextColorResource(colorResource: Flowable<Int>) =
    addSetter(colorResource) { setHintTextColor(color(it)) }

fun TextView.setTextStrikeThru(strikeThru: Flowable<Boolean>) =
    addSetter(strikeThru) { setTextStrikeThru(it) }

/**
 * GETTER
 */

fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxField<T>, mapper: (Editable?) -> T?) {
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanges.set(mapper(s))
        }
    })
}

fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxItem<T>, mapper: (Editable) -> T) {
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.apply { afterTextChanges.set(mapper(this)) }
        }
    })
}

fun EditText.afterTextChanges(afterTextChanges: RxItem<String>) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }

fun EditText.afterTextChanges(afterTextChanges: RxString) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }


fun <T : Any> EditText.beforeTextChanges(
    beforeTextChanges: RxField<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    textWatcher.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanges.set(mapper(BeforeEditTextChanges(s, start, count, after)))
        }
    })
}

fun EditText.beforeTextChanges(beforeTextChanges: RxField<BeforeEditTextChanges>) =
    beforeTextChanges(beforeTextChanges) { it }


fun <T : Any> EditText.onTextChanges(onTextChanges: RxField<T>, mapper: (OnEditTextChanges) -> T) {
    textWatcher.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanges.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}

fun EditText.onTextChanges(onTextChanges: RxField<OnEditTextChanges>) =
    onTextChanges(onTextChanges) { it }
