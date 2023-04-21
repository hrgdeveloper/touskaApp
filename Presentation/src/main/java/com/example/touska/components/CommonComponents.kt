package com.example.touska.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.unit.dp
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun VerticalSmallSpacer() {
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small_margin))
}


@Composable
fun VerticalDefaultMargin() {
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin))
}

@Composable
fun VerticalDefaultMarginBigger() {
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin_bigger))
}

@Composable
fun CustomDivider() {
    Divider(
        color = MaterialTheme.customColorsPalette.divider_color,
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
    )
}

@Composable
fun CircularProgressBox() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ConfirmButton(
    onclick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = {
            onclick()
        },
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = MaterialTheme.spacing.default_margin),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        shape = RoundedCornerShape(4.dp)
    ) {
        content()
    }
}

@Composable
fun DrawableText(
    text: String,
    icon: Painter,
    style: TextStyle = TextStyle(fontFamily = iranSansFamily),
    tint: Color? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        tint?.let {
            Icon(
                painter = icon, contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = tint
            )
        }?: kotlin.run {
            Icon(
                painter = icon, contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colors.surface
            )
        }

        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = style)
    }
}

@Composable
fun DrawableText(
    text: String,
    icon: ImageVector,
    style: TextStyle = TextStyle(fontFamily = iranSansFamily),
    tint: Color? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        tint?.let {
            Icon(
                imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp),
                tint = tint
            )
        }?: kotlin.run {
            Icon(
                imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colors.surface,
            )
        }

        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = style)
    }
}

@Composable
fun DrawableTextImage(
    text: String,
    icon: ImageVector,
    style: TextStyle = TextStyle(fontFamily = iranSansFamily),
) {
    Row(verticalAlignment = Alignment.CenterVertically) {

            Image(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
           Spacer(modifier = Modifier.width(4.dp))
            Text(text = text, style = style)


    }
}
@Composable
fun DrawableTextImage(
    text: String,
    icon: Painter,
    style: TextStyle = TextStyle(fontFamily = iranSansFamily),
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = icon, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = style)


    }
}
