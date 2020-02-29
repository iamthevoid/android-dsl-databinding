package iam.thevoid.noxml

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun containsInStackTrace(method : String) =
    Thread.currentThread().stackTrace.any { it.methodName == method }