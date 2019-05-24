package thevoid.iam.components.widget.util

const val DEPTH = 4

fun key(): String = with(Thread.currentThread().stackTrace) {
    if (size < DEPTH + 1) "" else with(get(DEPTH)) {
        Class.forName(className).methods.find {
            it.name == methodName
        }
    }?.let { method ->
        """${method.name}_${method.typeParameters.let { typeParameters ->
            if (typeParameters.isNullOrEmpty())
                ""
            else
                method.typeParameters[0]?.bounds.let {
                    if (it.isNullOrEmpty()) "" else (it[0] as? Class<*>)?.simpleName ?: ""
                }
        }}"""
    } ?: ""
}