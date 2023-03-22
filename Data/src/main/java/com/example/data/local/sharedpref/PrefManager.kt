package com.example.data.local.sharedpref


import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import javax.inject.Inject

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


     fun <T> getValue(key: String,clazz: Class<T>, defaultValue : T) : T {
          return when (clazz) {
               String::class.java -> sharedPreferences.getString(key, defaultValue as String) as T
               Int::class.java -> sharedPreferences.getInt(key, defaultValue as Int) as T
               Long::class.java -> sharedPreferences.getLong(key, defaultValue as Long) as T
               Boolean::class.java -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
               Float::class.java -> sharedPreferences.getFloat(key, defaultValue as Float) as T
               else -> throw IllegalArgumentException("Unsupported value type: ${clazz.name}")
          }
     }

     companion object  {
          const val IS_LOGIN ="is_login"
          const val TOKEN ="token"
     }

}