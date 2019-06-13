package thevoid.iam.components.rx

import io.reactivex.*
import thevoid.iam.components.rx.fields.RxBoolean
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

open class RxLoading {

    private val loading by lazy { RxBoolean() }

    val asFlowable: Flowable<Boolean>
        get() = loading.observe()

    val now: Boolean
        get() = loading.get()

    private val count = AtomicInteger(0)

    private fun inc() {
        if (count.incrementAndGet() == 1) {
            loading.set(true)
        }
    }

    private fun dec() {
        if (count.decrementAndGet() == 0) {
            loading.set(false)
        }
    }

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

    fun <T> flowable(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream.doOnSubscribe { loadingStarted(loading) }
            .doOnCancel { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnNext { loadingFinished(loading) }
    }

    fun <T> observable(): ObservableTransformer<T, T> = ObservableTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnNext { loadingFinished(loading) }
    }

    fun <T> single(): SingleTransformer<T, T> = SingleTransformer { upstream ->
        val loading = AtomicBoolean(false)
        upstream
            .doOnSubscribe { loadingStarted(loading) }
            .doOnDispose { loadingFinished(loading) }
            .doOnError { loadingFinished(loading) }
            .doOnSuccess { loadingFinished(loading) }
    }
}