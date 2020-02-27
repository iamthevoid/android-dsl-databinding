package iam.thevoid.noxml.coroutines.extensions

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import iam.thevoid.ae.color
import iam.thevoid.ae.setTextStrikeThru
import iam.thevoid.ae.string
import iam.thevoid.noxml.coroutines.fields.CoroutineCharSequence
import iam.thevoid.noxml.coroutines.fields.CoroutineField
import iam.thevoid.noxml.coroutines.fields.CoroutineItem
import iam.thevoid.noxml.coroutines.fields.CoroutineString
import iam.thevoid.noxml.extensions.textWatcher
import iam.thevoid.noxml.adapters.TextWatcherAdapter
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import kotlinx.coroutines.flow.Flow


fun <T : CharSequence> TextView.setText(textFlow: Flow<T>) =
    addSetter(textFlow) { text = it }

fun <T : CharSequence> TextView.setText(coroutineCharSequence: CoroutineCharSequence<T>) =
    setText(coroutineCharSequence.observe())

fun TextView.setText(coroutineString: CoroutineString) =
    setText(coroutineString.observe())

fun TextView.setTextResource(textResourceFlow: Flow<Int>) =
    addSetter(textResourceFlow) { text = string(it) }

fun TextView.setTextColor(colorFlow: Flow<Int>) =
    addSetter(colorFlow) { setTextColor(it) }

fun TextView.setTextColorResource(colorResFlow: Flow<Int>) =
    addSetter(colorResFlow) { setTextColor(color(it)) }

fun TextView.setHintTextColor(colorResFlow: Flow<Int>) =
    addSetter(colorResFlow) { setHintTextColor(it) }

fun TextView.setHintTextColorResourse(colorResFlow: Flow<Int>) =
    addSetter(colorResFlow) { setHintTextColor(color(it)) }

fun TextView.setTextStrikeThru(strikeThru: Flow<Boolean>) =
    addSetter(strikeThru) { setTextStrikeThru(it) }

fun TextView.afterTextChanges(coroutineField: CoroutineField<Editable>) =
    afterTextChanges(coroutineField) { it }

fun <T : Any> TextView.afterTextChanges(
    coroutineField: CoroutineField<T>,
    mapper: (Editable) -> T
) =
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.also { coroutineField.set(mapper(it)) }
        }
    })

fun TextView.afterTextChanges(coroutineItem: CoroutineItem<String>) =
    afterTextChanges(coroutineItem) { "$it" }

fun <T : Any> TextView.afterTextChanges(coroutineItem: CoroutineItem<T>, mapper: (Editable) -> T) =
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.also { coroutineItem.set(mapper(it)) }
        }
    })

fun TextView.afterTextChanges(coroutineString: CoroutineString) =
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.also { coroutineString.set("$it") }
        }
    })


fun EditText.beforeTextChanges(field: CoroutineField<BeforeEditTextChanges>) =
    beforeTextChanges(field) { it }

fun <T : Any> EditText.beforeTextChanges(
    field: CoroutineField<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    textWatcher.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            field.set(mapper(BeforeEditTextChanges(s, start, count, after)))
        }
    })
}

fun EditText.onTextChanges(coroutineField: CoroutineField<OnEditTextChanges>) =
    onTextChanges(coroutineField) { it }

fun <T : Any> EditText.onTextChanges(
    field: CoroutineField<T>,
    mapper: (OnEditTextChanges) -> T
) {
    textWatcher.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            field.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}