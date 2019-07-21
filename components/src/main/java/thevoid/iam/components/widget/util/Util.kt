package thevoid.iam.components.widget.util

import iam.thevoid.e.safe
import java.lang.reflect.ParameterizedType
import java.lang.reflect.TypeVariable

const val DEPTH = 5

fun key(): String = with(Thread.currentThread().stackTrace) {

    buildString {
        for (i in size - 1 downTo DEPTH + 1)
            append(this@with[i].methodName).append("_")

        append(if (size < DEPTH + 1) "" else with(this@with[DEPTH]) {
            Class.forName(className).methods.find { it.name == methodName }
        }
            ?.let {
                if (it.genericParameterTypes.size > 1) {
                    "${it.name}_${(it.genericParameterTypes.component2() as? ParameterizedType)?.actualTypeArguments?.let { types ->
                        if (types.isNotEmpty()) types.component1().let { type ->
                            (type as? TypeVariable<*>)?.bounds.let { types ->
                                (types?.firstOrNull() as? Class<*>)?.name
                            } ?: type.toString()
                        } else ""
                    }}"
                } else
                    "${it.name}_${it.typeParameters.let { typeParameters ->
                        typeParameters.firstOrNull()?.bounds.let { types ->
                            (types?.firstOrNull() as? Class<*>)?.name
                        }.safe()
                    }}"
            }.safe()
        )
    }
}