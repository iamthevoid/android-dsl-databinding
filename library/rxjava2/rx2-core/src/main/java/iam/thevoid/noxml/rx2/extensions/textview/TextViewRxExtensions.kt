package iam.thevoid.noxml.rx2.extensions.textview

import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import iam.thevoid.ae.color
import iam.thevoid.ae.setTextStrikeThru
import iam.thevoid.ae.string
import iam.thevoid.e.format
import iam.thevoid.e.safe
import iam.thevoid.noxml.change.textwatcher.BeforeEditTextChanges
import iam.thevoid.noxml.change.textwatcher.OnEditTextChanges
import iam.thevoid.noxml.core.local.adapters.TextWatcherAdapter
import iam.thevoid.noxml.core.local.extensions.textview.textWatcherDelegate
import iam.thevoid.noxml.rx2.data.fields.*
import iam.thevoid.noxml.rx2.data.onlyPresent
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import iam.thevoid.util.Optional
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor

fun <T : CharSequence> TextView.setText(text: Flowable<T>) =
    addSetter(text) { this.text = it }

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun <T : CharSequence> TextView.setText(text: RxCharSequence<T>) =
    setText(text.observe())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun TextView.setText(text: RxString) =
    setText(text.observe())

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun TextView.setText(text: RxInt) =
    setText(text.observe().map { "$it" })

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun TextView.setText(text: RxLong) =
    addSetter(text.observe().map { "$it" })

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun TextView.setText(text: RxFloat, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
fun TextView.setText(text: RxDouble, precision: Int? = null) =
    addSetter(text.observe().map { it.format(precision) })

fun TextView.setTextResource(textResource: Flowable<Int>) =
    setText(textResource.map { string(it) })

@Deprecated("Fields and Items will be removed in major version, use realization with Flowable instead")
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

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxField<T>, mapper: (Editable?) -> T?) {
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanges.set(mapper(s))
        }
    })
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.afterTextChanges(afterTextChanges: RxItem<T>, mapper: (Editable) -> T) {
    textWatcherDelegate.addAfterTextChangedCallback(object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            s?.apply { afterTextChanges.set(mapper(this)) }
        }
    })
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun EditText.afterTextChanges(afterTextChanges: RxItem<String>) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun EditText.afterTextChanges(afterTextChanges: RxString) =
    afterTextChanges(afterTextChanges) { "${it.safe()}" }


fun <T : Any> EditText.afterTextChanges(
    afterTextChanges: FlowableProcessor<T>,
    mapper: (Editable?) -> T?,
    unwrap: Flowable<Optional<T>>.() -> Flowable<T>
) {
    addSetter(Flowable.create<Optional<T>>({
        val callback = object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                it.onNext(Optional.of(mapper(s)))
            }
        }
        textWatcherDelegate.addAfterTextChangedCallback(callback)
        it.setCancellable { textWatcherDelegate.removeAfterTextChangedCallback(callback) }
    }, BackpressureStrategy.LATEST).unwrap().doOnNext(afterTextChanges::onNext))
}

fun EditText.afterTextChanges(
    afterTextChanges: FlowableProcessor<String>,
    unwrap: Flowable<Optional<String>>.() -> Flowable<String> = { onlyPresent() }
) {
    afterTextChanges(afterTextChanges, mapper = { it?.toString() }, unwrap)
}


@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
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

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun EditText.beforeTextChanges(beforeTextChanges: RxField<BeforeEditTextChanges>) =
    beforeTextChanges(beforeTextChanges) { it }

fun <T : Any> EditText.beforeTextChanges(
    beforeTextChanges: FlowableProcessor<T>,
    mapper: (BeforeEditTextChanges) -> T
) {
    addSetter(Flowable.create<T>({
        val callback = object : TextWatcherAdapter() {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                it.onNext(mapper(BeforeEditTextChanges(s, start, count, after)))
            }
        }
        textWatcherDelegate.addBeforeTextChangedCallback(callback)
        it.setCancellable { textWatcherDelegate.removeBeforeTextChangedCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(beforeTextChanges::onNext))
}

fun EditText.beforeTextChanges(beforeTextChanges: FlowableProcessor<BeforeEditTextChanges>) =
    beforeTextChanges(beforeTextChanges, mapper = { it })


@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun <T : Any> EditText.onTextChanges(onTextChanges: RxField<T>, mapper: (OnEditTextChanges) -> T) {
    textWatcherDelegate.addOnTextChangedCallback(object : TextWatcherAdapter() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanges.set(mapper(OnEditTextChanges(s, start, before, count)))
        }
    })
}

@Deprecated("Fields and Items will be removed in major version, use realization with FlowableProcessor instead")
fun EditText.onTextChanges(onTextChanges: RxField<OnEditTextChanges>) =
    onTextChanges(onTextChanges) { it }

fun <T : Any> EditText.onTextChanges(
    onTextChanges: FlowableProcessor<T>,
    mapper: (OnEditTextChanges) -> T
) {
    addSetter(Flowable.create<T>({
        val callback = object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                it.onNext(mapper(OnEditTextChanges(s, start, before, count)))
            }
        }
        textWatcherDelegate.addOnTextChangedCallback(callback)
        it.setCancellable { textWatcherDelegate.removeOnTextChangedCallback(callback) }
    }, BackpressureStrategy.LATEST).doOnNext(onTextChanges::onNext))
}

fun EditText.onTextChanges(onTextChanges: FlowableProcessor<OnEditTextChanges>) =
    onTextChanges(onTextChanges) { it }
