@file:Suppress("unused")

package iam.thevoid.noxml.rx2.design.extensions.textinputlayout

import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.noxml.design.extensions.textinput.errorTextResource
import iam.thevoid.noxml.rx2.extensions.view.addSetter
import io.reactivex.Flowable

fun TextInputLayout.setErrorResource(errorResource : Flowable<Int>) =
    addSetter(errorResource) { errorTextResource = it }

fun TextInputLayout.setErrorEnabled(error : Flowable<Boolean>) =
    addSetter(error) { isErrorEnabled = it }