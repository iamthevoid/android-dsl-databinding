package iam.thevoid.noxml.coroutines.design.extensions

import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.noxml.coroutines.data.CoroutineBoolean
import iam.thevoid.noxml.coroutines.extensions.addSetter
import iam.thevoid.noxml.design.extensions.errorTextResource
import kotlinx.coroutines.flow.Flow

fun TextInputLayout.setErrorResource(errorResource : Flow<Int>) =
    addSetter(errorResource) { errorTextResource = it }

fun TextInputLayout.setErrorEnabled(error : CoroutineBoolean) =
    setErrorEnabled(error.observe())

fun TextInputLayout.setErrorEnabled(error : Flow<Boolean>) =
    addSetter(error) { isErrorEnabled = it }