package iam.thevoid.noxml.design.extensions

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.ae.color
import iam.thevoid.ae.string
import iam.thevoid.noxml.util.Internals

var TextInputLayout.errorTextResource : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@StringRes value) { error = string(value) }

var TextInputLayout.errorTextColorResource : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@ColorRes value) { errorTextColor = color(value) }

var TextInputLayout.errorTextColor : Int
    @Deprecated(Internals.NO_GETTER, level = DeprecationLevel.ERROR) get() = Internals.noGetter()
    set(@ColorInt value) { setErrorTextColor(ColorStateList.valueOf(value)) }