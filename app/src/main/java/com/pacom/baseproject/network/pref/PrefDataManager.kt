package com.pacom.baseproject.network.pref

import android.content.Context
import com.google.gson.Gson
import com.pacom.baseproject.utils.fromJson
import com.pacom.baseproject.utils.parseGson
import javax.inject.Inject

class PrefDataManager @Inject constructor(context: Context, gson: Gson) : PrefHelper {
    val mPref by lazy { context.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE) }

    companion object {
        val KEY_NAME = "KEY_NAME"
    }

    inline fun <reified T> put(key: String, value: T) {
        when (T::class) {
            Int::class.java -> mPref.edit().putInt(key, value as Int).apply()
            Float::class.java -> mPref.edit().putFloat(key, value as Float).apply()
            Long::class.java -> mPref.edit().putLong(key, value as Long).apply()
            Boolean::class.java -> mPref.edit().putBoolean(key, value as Boolean).apply()
            String::class.java -> mPref.edit().putString(key, value as String).apply()
            else -> {
                val json = T::class.parseGson()
                mPref.edit().putString(key, json).apply()
            }
        }
    }

    inline fun <reified T> get(key: String, defaultValue: T): T? {
        return when (T::class) {
            Int::class.java -> mPref.getInt(key, defaultValue as Int) as T
            Float::class.java -> mPref.getFloat(key, defaultValue as Float) as T
            Long::class.java -> mPref.getLong(key, defaultValue as Long) as T
            Boolean::class.java -> mPref.getBoolean(key, defaultValue as Boolean) as T
            String::class.java -> mPref.getString(key, defaultValue as String) as T
            else -> {
                val result = mPref.getString(key, null)
                Gson().fromJson<T>(result!!)
            }
        }
    }
    override fun getAccessToken(): String {
        return ""
    }
}