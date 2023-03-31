package com.example.touska.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun VerticalSmallSpacer(){
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small_margin))
}


@Composable
fun VerticalDefaultMargin(){
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin))
}
@Composable
fun VerticalDefaultMarginBigger(){
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.default_margin_bigger))
}

@Composable
fun customDivider() {
    Divider(
        color = MaterialTheme.customColorsPalette.divider_color,
        modifier = Modifier.height(0.8.dp).fillMaxWidth()
    )
}

@Composable
fun CircularProgressBox() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun ConfirmButton( onclick : ()->Unit,
                   content : @Composable ()->Unit
                  ) {
    Button(
        onClick = {
            onclick()
        },
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = MaterialTheme.spacing.default_margin),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
        shape = RoundedCornerShape(4.dp)
    ) {
       content()
    }
}

