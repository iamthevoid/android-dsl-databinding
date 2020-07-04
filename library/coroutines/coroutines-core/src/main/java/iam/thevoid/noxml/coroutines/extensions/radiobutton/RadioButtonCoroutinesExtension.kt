package iam.thevoid.noxml.coroutines.extensions.radiobutton

import android.widget.RadioButton
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.data.CoroutineField
import iam.thevoid.noxml.coroutines.data.CoroutineItem
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map


fun RadioButton.setChecked(checked : CoroutineField<Boolean>) =
        setChecked(checked.observe().filter { it.present }.map { it.notNull })

fun RadioButton.setChecked(checked : CoroutineBoolean) =
        setChecked(checked.observe())

fun RadioButton.setChecked(checked : CoroutineItem<Boolean>) =
        setChecked(checked.observe())

fun RadioButton.setChecked(checked : Flow<Boolean>) =
        addSetter(checked) { isChecked = it }