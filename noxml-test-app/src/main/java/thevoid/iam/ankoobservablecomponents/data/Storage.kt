package thevoid.iam.ankoobservablecomponents.data

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Single

object Storage {

    const val NAME = "local_storage"

    private var sharedPrefs: SharedPreferences? = null

    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) =
        sharedPrefs
            ?.edit()
            ?.putString(key, value)
            ?.apply()

    fun get(key: String, default: String?) =
        sharedPrefs?.getString(key, default)

    fun single(key: String, default: String = "") = Single.create<String> {
        get(key, default)?.also { res -> it.onSuccess(res) } ?: it.onError(IllegalArgumentException("Value for key=\"$key\" not found in storage"))
    }

}