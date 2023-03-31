package com.example.touska.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.example.shared.Resource

fun String.toastLong(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_LONG).show()
}
fun String.toastShort(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Resource.Failure.returnProperMessage(context: Context) : String {
    if (stringIdResource==0) {
        return message
    }else {
        return context.resources.getString(stringIdResource)
    }
}