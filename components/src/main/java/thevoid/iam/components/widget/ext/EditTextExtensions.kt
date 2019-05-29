package thevoid.iam.components.widget.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.StringRes
import iam.thevoid.e.format
import io.reactivex.Flowable
import org.jetbrains.anko.hintTextColor
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import thevoid.iam.components.R
import thevoid.iam.components.rx.fields.*
import thevoid.iam.components.widget.util.adapter.TextWatcherAdapter

fun EditText.afterTextChanges(rxEditable: RxString) =
    addGetter({
        afterChangedWatcher?.also { watcher -> removeTextChangedListener(watcher) }
        addTextChangedListener(afterChangedWatcher ?: object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                s?.apply { it.invoke("$this") }
            }
        }.also { watcher -> afterChangedWatcher = watcher })
    }, rxEditable)

fun EditText.beforeTextChanges(rxChanges: RxField<BeforeEditTextChanges>) =
    addGetter({
        beforeChangedWatcher?.also { watcher -> removeTextChangedListener(watcher) }
        addTextChangedListener(beforeChangedWatcher ?: object : TextWatcherAdapter() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                it.invoke(BeforeEditTextChanges(s, start, count, after))
            }
        }.also { watcher -> beforeChangedWatcher = watcher })
    }, rxChanges)

fun EditText.onTextChanges(rxChanges: RxField<OnEditTextChanges>) =
    addGetter({
        onChangedWatcher?.also { watcher -> removeTextChangedListener(watcher) }
        addTextChangedListener(onChangedWatcher ?: object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                it.invoke(OnEditTextChanges(s, start, before, count))
            }
        }.also { watcher -> onChangedWatcher = watcher })
    }, rxChanges)

fun EditText.setTextSilent(text: CharSequence) {
    userDefinedWatchers.forEach { watcher -> watcher?.also { removeTextChangedListener(it) } }
    setText(text)
    userDefinedWatchers.forEach { watcher -> watcher?.also { addTextChangedListener(it) } }
}

fun EditText.setTextResourceSilent(@StringRes text: Int) {
    userDefinedWatchers.forEach { watcher -> watcher?.also { removeTextChangedListener(it) } }
    setText(text)
    userDefinedWatchers.forEach { watcher -> watcher?.also { addTextChangedListener(it) } }
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
    addSetter(colorFlowable) { textColor = it }

fun EditText.setHintColor(colorFlowable: Flowable<Int>) =
    addSetter(colorFlowable) { hintTextColor = it }

fun EditText.setTextColorResourse(colorResFlowable: Flowable<Int>) =
    addSetter(colorResFlowable) { textColorResource = it }


private var EditText.afterChangedWatcher: TextWatcher?
    get() = getTag(R.id.afterChangedWatcher) as? TextWatcher
    set(watcher) = setTag(R.id.afterChangedWatcher, watcher)


private var EditText.beforeChangedWatcher: TextWatcher?
    get() = getTag(R.id.beforeChangedWatcher) as? TextWatcher
    set(watcher) = setTag(R.id.beforeChangedWatcher, watcher)


private var EditText.onChangedWatcher: TextWatcher?
    get() = getTag(R.id.onChangedWatcher) as? TextWatcher
    set(watcher) = setTag(R.id.onChangedWatcher, watcher)

private val EditText.userDefinedWatchers
    get() = listOf(beforeChangedWatcher, onChangedWatcher, afterChangedWatcher)

data class BeforeEditTextChanges(val s: CharSequence?, val start: Int, val count: Int, val after: Int)

data class OnEditTextChanges(val s: CharSequence?, val start: Int, val before: Int, val count: Int)