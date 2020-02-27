package iam.thevoid.noxml.demo.ui.mvvm.just

import androidx.lifecycle.ViewModel
import iam.thevoid.rxe.toFlowableLatest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class JustViewModel : ViewModel() {

    var colorVal : Long = 0xff000000

    val color by lazy {
        Observable.defer {
            Observable.interval(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map { colorVal.toInt() }
        }.doOnNext { if (colorVal <= 0xffffffff) colorVal++ else colorVal = 0xff000000 }.toFlowableLatest()
    }

}