package com.example.touska.screens.homeScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.data.BuildConfig
import com.example.touska.R
import com.example.touska.components.VerticalDefaultMargin
import com.example.touska.components.VerticalSmallSpacer
import com.example.touska.navigation.MainNavigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.mirror

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
        Column(
            Modifier
                .padding(all = MaterialTheme.spacing.default_margin)
                .verticalScroll(rememberScrollState())) {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.customColorsPalette.cardBack)
            ) {
                val (iv_project,iv_profile,tv_project,tv_email,spacer,tv_name) = createRefs()
                Image(painter = painterResource(id = R.drawable.project), contentDescription = null,
                     modifier = Modifier
                         .fillMaxWidth()
                         .height(260.dp)
                         .constrainAs(iv_project) {
                             top.linkTo(parent.top, 0.dp)
                         },
                    contentScale = ContentScale.Crop
                    )


                Image(   painter =
                if (user.profile!= null) {
                    rememberImagePainter(BuildConfig.BASE_IMAGE + user.profile)
                } else {
                    painterResource(id = R.drawable.ic_profile)

                }, contentDescription = null,
                    modifier = Modifier
                        .size(85.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .constrainAs(iv_profile) {
                            top.linkTo(iv_project.bottom)
                            bottom.linkTo(iv_project.bottom)
                            end.linkTo(iv_project.end, 15.dp)
                        },
                    contentScale = ContentScale.Crop
                    )

                Text(text = stringResource(id = R.string.projectName) + " : " + user.project!!.name, fontSize = 16.sp,
                    fontFamily = iranSansFamily,
                    modifier = Modifier.constrainAs(tv_project){
                        top.linkTo(iv_profile.bottom,8.dp)
                        start.linkTo(parent.start,16.dp)

                        }
                    )
                Text(text =  user.name, fontSize = 16.sp,
                    fontFamily = iranSansFamily,
                    modifier = Modifier.constrainAs(tv_name){
                        top.linkTo(tv_project.bottom,8.dp)
                        start.linkTo(parent.start,16.dp)
                    }
                )




                Spacer(modifier = Modifier.height(8.dp).fillMaxWidth().constrainAs(spacer){
                    top.linkTo(tv_name.bottom)
                })

            }

            VerticalDefaultMargin()

            //Users Card
            Card(backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                modifier = Modifier.clickable {
                    navController.navigate(MainNavigation.UserManage.route)
                }
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default_margin)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        Icon(painter = painterResource(id = R.drawable.ic_users), contentDescription =null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.users_manage))
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.mirror())

                }
            }


            VerticalDefaultMargin()

            //Blocs Card
            Card(backgroundColor = MaterialTheme.customColorsPalette.cardBack,
            modifier = Modifier.clickable {
                navController.navigate(MainNavigation.Bloc.route)
            }
                ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.default_margin)
                   ,
                   horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    Row() {
                        Icon(painter = painterResource(id = R.drawable.ic_blocs), contentDescription =null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.blocks_manage))
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null,Modifier.mirror())

                }
            }

            VerticalDefaultMargin()
            //Posts Card
            Card(backgroundColor = MaterialTheme.customColorsPalette.cardBack) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(MainNavigation.Post.route)
                    }
                    .padding(MaterialTheme.spacing.default_margin),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        Icon(imageVector = Icons.Default.Workspaces, contentDescription =null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.posts_manage))
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null,Modifier.mirror())

                }
            }

            VerticalDefaultMargin()

            //Contract Card
            Card(backgroundColor = MaterialTheme.customColorsPalette.cardBack) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(MainNavigation.Contract.route)
                    }
                    .padding(MaterialTheme.spacing.default_margin),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        Icon(imageVector = Icons.Default.Feed, contentDescription =null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(stringResource(R.string.contract_manage))
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null,Modifier.mirror())

                }
            }


            VerticalDefaultMargin()
            //WorkingTime Card
            Card(backgroundColor = MaterialTheme.customColorsPalette.cardBack) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(MainNavigation.WorkingTime.route)
                    }
                    .padding(MaterialTheme.spacing.default_margin),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row() {
                        Icon(imageVector = Icons.Default.Watch, contentDescription =null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = stringResource(R.string.manage_working_time))
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null,Modifier.mirror())

                }
            }

            VerticalDefaultMargin()


        }


    }


}