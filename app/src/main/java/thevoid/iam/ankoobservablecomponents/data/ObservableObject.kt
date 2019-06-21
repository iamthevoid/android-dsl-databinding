package thevoid.iam.ankoobservablecomponents.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import thevoid.iam.components.rx.RxLoading
import java.util.*
import java.util.concurrent.TimeUnit

object ObservableObject {

    val loading = RxLoading()

    val text = Observable.interval(1, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .compose(loading.observable())
        .map { randomString() }

    val number = Observable.interval(300, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .map { randomNumber() }
        .compose(loading.observable())

    fun randomString(): String = buildString {
        val random = Random()
        for (i in 0..7) {
            val upper = (random.nextInt(26) + 65).toChar()
            val lower = (random.nextInt(26) + 97).toChar()
            val nextLower = random.nextBoolean()
            append("${if (nextLower) lower else upper}")
        }
    }

    private fun randomNumber(): String = buildString {
        for (i in 0..5) {
            append("${Random().nextInt(10)}")
        }
    }
}