package com.example.touska.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.touska.R
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.mirror
import kotlinx.coroutines.launch


@Composable
fun FilterBox(title:Int,text:String,click : ()->Unit) {

    Text(text = stringResource(id = title), color = MaterialTheme.colors.surface,
         fontSize = 12.sp, fontFamily = iranSansFamily,
        textAlign = TextAlign.Start
        ,
         modifier = Modifier.fillMaxWidth().padding(start = MaterialTheme.spacing.default_margin+8.dp)
        )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default_margin)
            .height(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp, MaterialTheme.customColorsPalette.cardBack,
                RoundedCornerShape(8.dp)
            )
            .clickable {
                click()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.default_margin),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (text.isEmpty())
                    stringResource(id = R.string.select_filter)
                else text,
                style = TextStyle(color = if (text.isEmpty()) MaterialTheme.colors.surface else
                    MaterialTheme.colors.primary, fontFamily = iranSansFamily
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }

    }

}
