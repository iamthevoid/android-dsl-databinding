package iam.thevoid.noxml.rx2.data

import iam.thevoid.e.isNotNull
import iam.thevoid.util.Optional
import io.reactivex.Flowable

fun <T : Any> Flowable<Optional<T>>.onlyPresent() = filter { it.isNotNull() }.map { it.notNull }

fun <T : Any> Flowable<Optional<T>>.force() = map { it.notNull }