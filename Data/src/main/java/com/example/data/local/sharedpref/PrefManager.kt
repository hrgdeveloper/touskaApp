package com.example.data.local.sharedpref


import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import javax.inject.Inject
import kotlin.reflect.KClass

class PrefManager @Inject constructor(val sharedPreferences: SharedPreferences,val editor: Editor) {


     fun <T> setValue(key: String, value: T) {
          when (value) {
               is String -> editor.putString(key, value)
               is Int -> editor.putInt(key, value)
               is Long -> editor.putLong(key, value)
               is Boolean -> editor.putBoolean(key, value)
               is Float -> editor.putFloat(key, value)
               else -> throw IllegalArgumentException("Unsupported value type: ${value!!::class.java.name}")
          }
          editor.apply()
     }


     fun <T : Any> getValue(key: String, clazz: KClass<T>, defaultValue: T): T {
          return when (clazz) {
               String::class -> sharedPreferences.getString(key, defaultValue as? String ?: "") as T
               Int::class -> sharedPreferences.getInt(key, defaultValue as? Int ?: 0) as T
               Long::class -> sharedPreferences.getLong(key, defaultValue as? Long ?: 0L) as T
               Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as? Boolean ?: false) as T
               Float::class -> sharedPreferences.getFloat(key, defaultValue as? Float ?: 0f) as T
               else -> throw IllegalArgumentException("Unsupported value type: ${clazz.simpleName}")
          }
     }

     companion object  {
          const val IS_LOGIN ="is_login"
          const val TOKEN ="token"
     }

}