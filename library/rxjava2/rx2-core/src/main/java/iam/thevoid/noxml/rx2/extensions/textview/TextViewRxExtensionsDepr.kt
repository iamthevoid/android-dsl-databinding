package iam.thevoid.noxml.rx2.extensions.textview

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import iam.thevoid.e.format
import iam.thevoid.e.safe
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.core.local.adapters.TextWatcherAdapter
import iam.thevoid.noxml.core.local.extensions.textview.textWatcherDelegate
import iam.thevoid.noxml.rx2.data.fields.*
import iam.thevoid.noxml.rx2.extensions.view.addSetter

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun <T : CharSequence> TextView.setText(text: RxCharSequence<T>) =
    setText(text.observe())

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setText(text: RxString) =
    setText(text.observe())

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setText(text: RxInt) =
    setText(text.observe().map { "$it" })

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setText(text: RxLong) =
    addSetter(text.observe().map { "$it" })

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setText(text: RxFloat, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setText(text: RxDouble, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextView.setTextResource(textResource: RxInt) =
    setTextResource(textResource.observe())

// GETTER

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxField<T>, mapper: (Editable?) -> T?) {
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanges.set(mapper(s))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxItem<T>, mapper: (Editable) -> T) {
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.apply { afterTextChanges.set(mapper(this)) }
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun EditText.afterTextChanges(afterTextChanges: RxItem<String>) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun EditText.afterTextChanges(afterTextChanges: RxString) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }


@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.beforeTextChanges(
    beforeTextChanges: RxField<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    textWatcherDelegate.addBeforeTextChangedCallback(object : TextWatcherAdapter() {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanges.set(mapper(BeforeEditTextChanges(s, start, count, after)))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun EditText.beforeTextChanges(beforeTextChanges: RxField<BeforeEditTextChanges>) =
    beforeTextChanges(beforeTextChanges) { it }


@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.onTextChanges(onTextChanges: RxField<T>, mapper: (OnEditTextChanges) -> T) {
    textWatcherDelegate.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanges.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}

@Deprecated("Fields and Items will be removed in release version, use realization with FlowableProcessor instead")
fun EditText.onTextChanges(onTextChanges: RxField<OnEditTextChanges>) =
    onTextChanges(onTextChanges) { it }