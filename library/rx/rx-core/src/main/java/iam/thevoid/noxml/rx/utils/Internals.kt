package iam.thevoid.noxml.rx.utils

object Internals {

    const val NO_GETTER = "Property has no getter"

    fun noGetter() : Nothing = throw RuntimeException(NO_GETTER)

}