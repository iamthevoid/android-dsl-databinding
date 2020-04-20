package iam.thevoid.noxml.coroutines.design.extensions.textinputlayout

import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.extensions.view.addSetter
import iam.thevoid.noxml.design.extensions.textinput.errorTextResource
import kotlinx.coroutines.flow.Flow

fun TextInputLayout.setErrorResource(errorResource : Flow<Int>) =
    addSetter(errorResource) { errorTextResource = it }

fun TextInputLayout.setErrorEnabled(error : CoroutineBoolean) =
    setErrorEnabled(error.observe())

fun TextInputLayout.setErrorEnabled(error : Flow<Boolean>) =
    addSetter(error) { isErrorEnabled = it }