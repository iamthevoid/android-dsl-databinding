package thevoid.iam.components.widget.ext

import android.text.Editable
import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.ae.color
import iam.thevoid.ae.moveCursorToEnd
import iam.thevoid.ae.requestSoftInput
import iam.thevoid.ae.resetFocus
import iam.thevoid.e.format
import io.reactivex.Flowable
import thevoid.iam.components.R
import thevoid.iam.components.rx.fields.*
import thevoid.iam.components.widget.delegate.TextWatcherDelegate
import thevoid.iam.components.widget.adapter.TextWatcherAdapter

private val EditText.textWatcher: TextWatcherDelegate
    get() = ((getTag(R.id.textWatcher) as? TextWatcherDelegate)
        ?: TextWatcherDelegate().also {
            setTag(R.id.textWatcher, it)
            addTextChangedListener(it)
        })

fun EditText.afterTextChanges(rxEditable: RxField<Editable>) = afterTextChanges(rxEditable) { it }

fun <T : Any> EditText.afterTextChanges(rxEditable: RxField<T>, mapper: (Editable) -> T) =
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

fun EditText.beforeTextChanges(rxChanges: RxField<BeforeEditTextChanges>) = beforeTextChanges(rxChanges) { it }

fun <T : Any> EditText.beforeTextChanges(rxChanges: RxField<T>, mapper: (BeforeEditTextChanges) -> T) =
    addGetter({
        textWatcher.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                it.invoke(mapper(BeforeEditTextChanges(s, start, count, after)))
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

fun EditText.setTextSilent(text: CharSequence) {
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    moveCursorToEnd()
    addTextChangedListener(watcher)
}

fun EditText.setTextResourceSilent(@StringRes text: Int) {
    val watcher = textWatcher
    removeTextChangedListener(watcher)
    setText(text)
    moveCursorToEnd()
    addTextChangedListener(watcher)
}

fun <T : CharSequence> EditText.setText(rxString: RxCharSequence<T>) =
    addSetter(rxString.observe()) { setTextSilent(it) }

fun <T : CharSequence> EditText.setText(textFlowable: Flowable<T>) =
    addSetter(textFlowable) { setTextSilent(it) }

fun EditText.setText(rxInt: RxInt) =
    addSetter(rxInt.observe()) { setTextSilent("$it") }

fun EditText.setText(rxLong: RxLong) =
    addSetter(rxLong.observe()) { setTextSilent("$it") }

fun EditText.setText(rxFloat: RxFloat, precision: Int? = null) =
    addSetter(rxFloat.observe()) { setTextSilent(it.format(precision)) }

fun EditText.setText(rxDouble: RxDouble, precision: Int? = null) =
    addSetter(rxDouble.observe()) { setTextSilent(it.format(precision)) }

fun EditText.setTextResource(rxIntResource: RxInt) =
    addSetter(rxIntResource.observe()) { setTextResourceSilent(it) }

fun EditText.setTextResource(textResourceFlowable: Flowable<Int>) =
    addSetter(textResourceFlowable) { setTextResourceSilent(it) }

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


data class BeforeEditTextChanges(val s: CharSequence?, val start: Int, val count: Int, val after: Int)

data class OnEditTextChanges(val s: CharSequence?, val start: Int, val before: Int, val count: Int)