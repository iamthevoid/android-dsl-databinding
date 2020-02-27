package iam.thevoid.noxml.demo.util

import android.text.InputFilter
import iam.thevoid.e.safe

enum class Op {
    REPLACE,
    DELETE,
    INPUT;

    companion object {
        fun get(start: Int, end: Int) =
            when {
                end == start -> INPUT
                end < start -> DELETE
                else -> REPLACE
            }
    }
}

fun rateInputFilter(beforePoint: Int, afterPoint: Int) =
    InputFilter { source, _, _, dest, dstart, dend ->

        val greenLight = Regex("-*\\d{0,$beforePoint}(\\.\\d{0,$afterPoint})*")
        when (Op.get(dstart, dend)) {
            Op.INPUT, Op.REPLACE -> {
                val res =  buildString {
                    append(dest.substring(0 until dend))
                    append(source)
                    append(dest.substring(dend until dest.length))
                }
                if (res matches greenLight) null else ""
            }
            Op.DELETE -> null
        }
    }