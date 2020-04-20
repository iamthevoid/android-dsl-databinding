package iam.thevoid.noxml.rx2.design.extensions.textinputlayout

import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.noxml.design.extensions.textinput.errorTextResource
import io.reactivex.Flowable
import iam.thevoid.noxml.rx2.data.fields.RxBoolean
import iam.thevoid.noxml.rx2.extensions.view.addSetter

fun TextInputLayout.setErrorResource(errorResource : Flowable<Int>) =
    addSetter(errorResource) { errorTextResource = it }

fun TextInputLayout.setErrorEnabled(error : RxBoolean) =
    setErrorEnabled(error.observe())

fun TextInputLayout.setErrorEnabled(error : Flowable<Boolean>) =
    addSetter(error) { isErrorEnabled = it }