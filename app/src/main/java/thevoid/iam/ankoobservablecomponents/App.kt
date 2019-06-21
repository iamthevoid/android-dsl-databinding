package thevoid.iam.ankoobservablecomponents

import androidx.multidex.MultiDexApplication
import thevoid.iam.ankoobservablecomponents.data.Storage

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Storage.init(this)
    }
}