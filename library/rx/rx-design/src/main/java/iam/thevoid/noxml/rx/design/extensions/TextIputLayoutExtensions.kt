package iam.thevoid.noxml.rx.design.extensions

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.ae.color
import iam.thevoid.ae.string
import io.reactivex.Flowable
import iam.thevoid.noxml.rx.rxdata.fields.RxBoolean
import iam.thevoid.noxml.rx.extensions.addSetter
import iam.thevoid.noxml.rx.utils.Internals


var TextInputLayout.errorTextResourse : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@StringRes value) { error = string(value) }

var TextInputLayout.errorTextColorResourse : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@ColorRes value) { errorTextColor = color(value) }

var TextInputLayout.errorTextColor : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@ColorInt value) { setErrorTextColor(ColorStateList.valueOf(value)) }

fun TextInputLayout.setError(errorRes : Flowable<Int>) =
    addSetter(errorRes) { errorTextResourse = it }

fun TextInputLayout.setErrorEnabled(error : RxBoolean) =
    addSetter(error.observe()) { isErrorEnabled = it }

fun TextInputLayout.setErrorEnabled(error : Flowable<Boolean>) =
    addSetter(error) { isErrorEnabled = it }