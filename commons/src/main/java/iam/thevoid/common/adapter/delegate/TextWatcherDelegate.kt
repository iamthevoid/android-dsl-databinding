package iam.thevoid.common.adapter.delegate

import android.text.Editable
import android.text.TextWatcher

class TextWatcherDelegate : TextWatcher {

    private val afterTextChangedCallback by lazy { mutableListOf<TextWatcher>() }
    private val beforeTextChangedCallback by lazy { mutableListOf<TextWatcher>() }
    private val onTextChangedCallback by lazy { mutableListOf<TextWatcher>() }


    fun addAfterTextChangedCallback(callback: TextWatcher) {
        afterTextChangedCallback.add(callback)
    }

    fun addBeforeTextChangedCallback(callback: TextWatcher) {
        beforeTextChangedCallback.add(callback)
    }

    fun addOnTextChangedCallback(callback: TextWatcher) {
        onTextChangedCallback.add(callback)
    }


    fun removeAfterTextChangedCallback(callback: TextWatcher) {
        afterTextChangedCallback.remove(callback)
    }

    fun removeBeforeTextChangedCallback(callback: TextWatcher) {
        beforeTextChangedCallback.remove(callback)
    }

    fun removeOnTextChangedCallback(callback: TextWatcher) {
        onTextChangedCallback.remove(callback)
    }


    override fun afterTextChanged(s: Editable?) =
        afterTextChangedCallback.forEach { it.afterTextChanged(s) }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
        beforeTextChangedCallback.forEach { it.beforeTextChanged(s, start, count, after) }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
        onTextChangedCallback.forEach { it.onTextChanged(s, start, before, count) }

}
