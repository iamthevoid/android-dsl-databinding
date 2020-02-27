package iam.thevoid.noxml.demo.data.api.jsonadapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

class StringDateAdapter : TypeAdapter<Date>() {

    private val datePattern = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun read(`in`: JsonReader?): Date? {
        if (`in`?.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }

        return `in`?.nextString()?.let { datePattern.parse(it) }
    }

    override fun write(out: JsonWriter?, value: Date?) {
        if (value == null) {
            out?.nullValue()
            return
        }
        out?.value(datePattern.format(value))
    }
}