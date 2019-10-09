package thevoid.iam.rx.widget.ext

import android.text.Editable
import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.*
import iam.thevoid.common.adapter.adapters.TextWatcherAdapter
import iam.thevoid.common.adapter.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.common.adapter.change.textwatcher.OnEditTextChanges
import iam.thevoid.common.adapter.delegate.TextWatcherDelegate
import iam.thevoid.e.format
import io.reactivex.Flowable
import thevoid.iam.rx.R
import thevoid.iam.rx.rxdata.fields.*

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

fun <T : Any> EditText.afterTextChanges(rxEditable: RxField<T>, mapper: (Editable) -> T) =
    addGetter({
        textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                s?.apply { it.invoke(mapper(this)) }
            }
        })
    }, rxEditable)

fun <T : Any> EditText.afterTextChanges(rxEditable: RxItem<T>, mapper: (Editable) -> T) =
    addGetter({
        textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                s?.apply { it.invoke(mapper(this)) }
            }
        })
    }, rxEditable)

fun EditText.afterTextChanges(rxEditable: RxString) =
    addGetter({
        textWatcher.addAfterTextChangedCallback(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                s?.apply { it.invoke("$this") }
            }
        })
    }, rxEditable)

fun EditText.beforeTextChanges(rxChanges: RxField<BeforeEditTextChanges>) =
    beforeTextChanges(rxChanges) { it }

fun <T : Any> EditText.beforeTextChanges(
    rxChanges: RxField<T>,
    mapper: (BeforeEditTextChanges) -> T
) =
    addGetter({
        textWatcher.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                it.invoke(
                    mapper(
                        BeforeEditTextChanges(
                            s,
                            start,
                            count,
                            after
                        )
                    )
                )
            }
        })
    }, rxChanges)

fun EditText.onTextChanges(rxChanges: RxField<OnEditTextChanges>) = onTextChanges(rxChanges) { it }

fun <T : Any> EditText.onTextChanges(rxChanges: RxField<T>, mapper: (OnEditTextChanges) -> T) =
    addGetter({
        textWatcher.addOnTextChangedCallback(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                it.invoke(mapper(OnEditTextChanges(s, start, before, count)))
            }
        })
    }, rxChanges)

/**
 * MAINTENANCE
 */

private val EditText.textWatcher: TextWatcherDelegate
    get() = ((getTag(R.id.textWatcher) as? TextWatcherDelegate)
        ?: TextWatcherDelegate().also {
            setTag(R.id.textWatcher, it)
            addTextChangedListener(it)
        })