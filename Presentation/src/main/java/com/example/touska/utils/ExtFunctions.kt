package com.example.touska.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast

fun String.toastLong(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}
fun String.toastShort(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()