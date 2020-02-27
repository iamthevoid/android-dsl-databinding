package iam.thevoid.noxml.demo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import io.reactivex.Single
import iam.thevoid.noxml.demo.data.db.AppDatabase

object Storage {

    const val DB_NAME = "no-xml-test-app.db"
    const val NAME = "local_storage"

    private var sharedPrefs: SharedPreferences? = null

    private var db: AppDatabase? = null

    val database
        get() = db ?: throw IllegalArgumentException("App Database not initialized")

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        db = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }

    fun put(key: String, value: String) =
        sharedPrefs
            ?.edit()
            ?.putString(key, value)
            ?.apply()

    fun get(key: String, default: String? = null) =
        sharedPrefs?.getString(key, default)

    fun single(key: String, default: String = "") = Single.create<String> {
        get(key, default)?.also { res -> it.onSuccess(res) } ?: it.onError(
            IllegalArgumentException(
                "Value for key=\"$key\" not found in storage"
            )
        )
    }

}