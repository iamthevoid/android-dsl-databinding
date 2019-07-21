package thevoid.iam.components.widget.util

object Internals {

    const val NO_GETTER = "Property has no getter"

    fun noGetter() : Nothing = throw RuntimeException(NO_GETTER)

}