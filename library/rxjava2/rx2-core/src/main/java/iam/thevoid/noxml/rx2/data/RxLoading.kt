package iam.thevoid.noxml.rx2.data

import io.reactivex.*
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

open class RxLoading {

    fun observe() = processor.doOnNext { println("$it subscribers") }.map { it > 0 }.doOnNext { println(if (it) "loading" else "not loading") }

    private val processor = BehaviorProcessor.createDefault(0)

    private val count = AtomicInteger(0)

    private fun inc() = processor.onNext(count.incrementAndGet())

    private fun dec() = processor.onNext(count.decrementAndGet())

    private fun loadingStarted(loading: AtomicBoolean) {
        if (!loading.get()) {
            loading.set(true)
            inc()
        }
    }

    private fun loadingFinished(loading: AtomicBoolean) {
        if (loading.get()) {
            loading.set(false)
            dec()
        }
    }

    fun completable(): CompletableTransformer = CompletableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnComplete { loadingFinished(loading) }
    }

    fun <T> flowableUntilNext(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream.doOnSubscribe { loadingStarted(loading) }
            .doOnCancel { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnNext { loadingFinished(loading) }
    }

    fun <T> flowableUntilComplete(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream.doOnSubscribe { loadingStarted(loading) }
            .doOnCancel { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnComplete { loadingFinished(loading) }
    }

    fun <T> observableUntilNext(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnNext { loadingFinished(loading) }
    }

    fun <T> observableUntilComplete(): ObservableTransformer<T, T> =
        ObservableTransformer { upstream ->
            val loading = AtomicBoolean(false)
            upstream
                .doOnComplete { loadingFinished(loading) }
                .doOnSubscribe { loadingStarted(loading) }
                .doOnDispose { loadingFinished(loading) }
                .doOnError { loadingFinished(loading) }
        }

    fun <T> single(): SingleTransformer<T, T> = SingleTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnSuccess { loadingFinished(loading) }
    }

    fun <T> maybe(): MaybeTransformer<T, T> = MaybeTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnSuccess { loadingFinished(loading) }
            .doOnComplete { loadingFinished(loading) }
    }
}