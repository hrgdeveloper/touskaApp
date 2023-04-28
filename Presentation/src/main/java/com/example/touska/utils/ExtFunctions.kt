package com.example.touska.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
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

fun Int.requestValue() : Int?{
    return if (this==0) {
        null
    }else {
        this
    }
}
fun String.requestValue() : String?{
    return if (this.isEmpty()) {
        null
    }else {
        this
    }
}



@Stable
fun Modifier.mirror(): Modifier = composed {
    if (LocalLayoutDirection.current == LayoutDirection.Rtl)
        this.scale(scaleX = -1f, scaleY = 1f)
    else
        this
}