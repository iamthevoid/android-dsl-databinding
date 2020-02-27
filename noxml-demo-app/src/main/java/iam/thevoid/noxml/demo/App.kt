package iam.thevoid.noxml.demo

import androidx.multidex.MultiDexApplication
import iam.thevoid.noxml.demo.data.Storage

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Storage.init(this)
    }
}