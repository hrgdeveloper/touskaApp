package com.example.touska.screens.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.touska.R
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing

@Composable
fun homeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val user = homeViewModel.user.observeAsState().value

    LaunchedEffect(key1 = Unit) {
        homeViewModel.getUser()
    }

    user?.let {
        Column(Modifier.padding(all = MaterialTheme.spacing.default_margin)) {
          Card(shape = RoundedCornerShape(15.dp), elevation = 4.dp) {
               Column(Modifier.fillMaxWidth()) {
                   // The bottom image
                   Image(
                       painter = painterResource(R.drawable.art),
                       contentDescription = "Bottom Image",
                       contentScale = ContentScale.FillBounds,
                       modifier = Modifier
                           .height(200.dp)
                           .fillMaxWidth()
                   )

                   Box(modifier = Modifier.fillMaxWidth()) {
                       Image(
                           painter = painterResource(R.drawable.art),
                           contentDescription = "Top Image",
                           contentScale = ContentScale.FillBounds,
                           modifier = Modifier
                               .width(60.dp)
                               .height(60.dp)
                               .clip(CircleShape)
                               .align(Alignment.BottomStart)
                               .offset(y = -30.dp)
                               .border(2.dp, Color.White, CircleShape)
                       )
                   }

                   // The top image

                   Text(text = user.project.name, fontFamily = iranSansFamily, fontWeight = FontWeight.Bold, fontSize = 25.sp)
               }




          }


        }


    }


}