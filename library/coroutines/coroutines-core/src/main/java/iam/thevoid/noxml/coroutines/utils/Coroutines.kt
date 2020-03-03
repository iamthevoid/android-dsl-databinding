package iam.thevoid.noxml.coroutines.utils

import kotlinx.coroutines.flow.Flow

object Coroutines {
    fun<T1, T2> combine(flow : Flow<T1>, flow2 : Flow<T2>) =
        kotlinx.coroutines.flow.combine(flow, flow2) { t1, t2 -> Pair(t1, t2) }
}