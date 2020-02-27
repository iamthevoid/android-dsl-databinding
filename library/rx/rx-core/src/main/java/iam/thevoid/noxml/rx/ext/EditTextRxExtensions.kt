package iam.thevoid.noxml.rx.ext

import android.text.Editable
import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.*
import iam.thevoid.noxml.adapters.TextWatcherAdapter
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.e.format
import io.reactivex.Flowable
import iam.thevoid.noxml.extensions.textWatcher
import iam.thevoid.noxml.rx.rxdata.fields.*

/**
 * SETTER
 */

fun EditText.setTextSilent(text: CharSequence, moveCursorToEnd: Boolean = true) {
    if (text.toString() == this.text.toString())
        return
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}

fun EditText.setTextResourceSilent(@StringRes text: Int, moveCursorToEnd: Boolean = true) {
    if (string(text) == "${this.text}")
        return
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    addTextChangedListener(watcher)
    if (moveCursorToEnd) {
        post {
            moveCursorToEnd()
            requestSoftInput()
        }
    }
}

fun <T : CharSequence> EditText.setText(
    rxString: RxCharSequence<T>,
    moveCursorToEnd: Boolean = true
) =
    addSetter(rxString.observe()) { setTextSilent(it, moveCursorToEnd) }

fun <T : CharSequence> EditText.setText(
    textFlowable: Flowable<T>,
    moveCursorToEnd: Boolean = true
) =
    addSetter(textFlowable) { setTextSilent(it, moveCursorToEnd) }

fun EditText.setText(rxInt: RxInt, moveCursorToEnd: Boolean = true) =
    addSetter(rxInt.observe()) { setTextSilent("$it", moveCursorToEnd) }

fun EditText.setText(rxLong: RxLong, moveCursorToEnd: Boolean = true) =
    addSetter(rxLong.observe()) { setTextSilent("$it", moveCursorToEnd) }

fun EditText.setText(rxFloat: RxFloat, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    addSetter(rxFloat.observe()) { setTextSilent(it.format(precision), moveCursorToEnd) }

fun EditText.setText(rxDouble: RxDouble, precision: Int? = null, moveCursorToEnd: Boolean = true) =
    addSetter(rxDouble.observe()) { setTextSilent(it.format(precision), moveCursorToEnd) }

fun EditText.setTextResource(rxIntResource: RxInt, moveCursorToEnd: Boolean = true) =
    addSetter(rxIntResource.observe()) { setTextResourceSilent(it, moveCursorToEnd) }

fun EditText.setTextResource(textResourceFlowable: Flowable<Int>, moveCursorToEnd: Boolean) =
    addSetter(textResourceFlowable) { setTextResourceSilent(it, moveCursorToEnd) }

fun EditText.setTextColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { setTextColor(it) }

fun EditText.setHintColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { setHintTextColor(it) }

fun EditText.setTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { setTextColor(color(it)) }

fun EditText.setRequestInput(rxBoolean: RxBoolean) =
    setRequestInput(rxBoolean.observe())

fun EditText.setRequestInput(requestInput: Flowable<Boolean>) =
    addSetter(requestInput) {
        val focusListener = onFocusChangeListener
        onFocusChangeListener = null
        if (it) {
            post {
                requestSoftInput()
                onFocusChangeListener = focusListener
            }
        } else {
            resetFocus()
            onFocusChangeListener = focusListener
        }
    }

/**
 * GETTER
 */

fun EditText.afterTextChanges(rxEditable: RxField<Editable>) = afterTextChanges(rxEditable) { it }

fun EditText.afterTextChanges(item: RxItem<String>) = afterTextChanges(item) { "$it" }

fun <T : Any> EditText.afterTextChanges(rxField: RxField<T>, mapper: (Editable) -> T) {
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.apply { rxField.set(mapper(this)) }
        }
    })
}

fun <T : Any> EditText.afterTextChanges(rxItem: RxItem<T>, mapper: (Editable) -> T) {
    textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.apply { rxItem.set(mapper(this)) }
        }
    })
}

fun EditText.afterTextChanges(rxString: RxString) {
        textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                s?.apply { rxString.set("$this") }
            }
        })
    }

fun EditText.beforeTextChanges(rxChanges: RxField<BeforeEditTextChanges>) =
    beforeTextChanges(rxChanges) { it }

fun <T : Any> EditText.beforeTextChanges(
    rxField: RxField<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    textWatcher.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            rxField.set(mapper(BeforeEditTextChanges(s, start, count, after)))
        }
    })
}

fun EditText.onTextChanges(rxChanges: RxField<OnEditTextChanges>) = onTextChanges(rxChanges) { it }

fun <T : Any> EditText.onTextChanges(rxField: RxField<T>, mapper: (OnEditTextChanges) -> T) {
    textWatcher.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            rxField.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}