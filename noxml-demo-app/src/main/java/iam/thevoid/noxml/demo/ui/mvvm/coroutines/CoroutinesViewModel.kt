package iam.thevoid.noxml.demo.ui.mvvm.coroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import iam.thevoid.coroutines.repeatFlow
import iam.thevoid.coroutines.share
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import iam.thevoid.noxml.demo.data.db.entity.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

class CoroutinesViewModel : ViewModel(), CoroutineScope by CoroutineScope(IO) {

    @UseExperimental(ExperimentalTime::class)
    val flw = repeatFlow(delay = 3.seconds) {
        val color = Color(value = "#000000")
        val red = Random.nextInt(0x100)
        val green = Random.nextInt(0x100)
        val blue = Random.nextInt(0x100)
        val value =
            "#${red.to2digitsString()}${green.to2digitsString()}${blue.to2digitsString()}"
        color.value = value
        emit(color)
    }.share(this)
        .onEach { Log.i("CoroutinesViewModel", "$it") }
        .map { it.value }

//    val flw = Storage.database.colorDao.colorrFlow()
//        .onEach { Log.i(javaClass.name, "${Thread.currentThread().name} $it") }
//        .map { it.item?.value.safe("#000000") }
//        .share(GlobalScope)

    private fun Int.to2digitsString() =
        toString(16).let {
            require(!(it.isEmpty() || it.length > 2)) { "Illegal int $this" }
            if (it.length == 1) "0$it" else it
        }

}