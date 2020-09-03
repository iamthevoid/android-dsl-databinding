package iam.thevoid.noxml.rx2.design.extensions.textinputlayout

import com.google.android.material.textfield.TextInputLayout
import iam.thevoid.noxml.rx2.data.fields.RxBoolean

@Deprecated("Fields and Items will be removed in release version, use realization with Flowable instead")
fun TextInputLayout.setErrorEnabled(error : RxBoolean) =
    setErrorEnabled(error.observe())