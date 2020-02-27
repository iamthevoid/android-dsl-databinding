package iam.thevoid.noxml.demo.ui.mvvm.coroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import iam.thevoid.noxml.demo.data.db.entity.Color
import iam.thevoid.noxml.coroutines.share
import kotlin.random.Random

class CoroutinesViewModel : ViewModel() {

    val flw = flow {

        while (true) {
            val color = Color(value = "#000000")
            val red = Random.nextInt(0x100)
            val green = Random.nextInt(0x100)
            val blue = Random.nextInt(0x100)
            val value =
                "#${red.to2digitsString()}${green.to2digitsString()}${blue.to2digitsString()}"
            color.value = value
            emit(color)
            delay(3000)
        }
    }.share(GlobalScope)
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