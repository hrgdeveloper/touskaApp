package com.example.touska.screens.profileScreen

import android.annotation.SuppressLint
import android.content.Intent

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.data.BuildConfig
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.navigation.MainNavigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.ui.theme.transBack
import com.example.touska.utils.UserTypes
import com.example.touska.utils.mirror

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson


import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun profileScreen(
    navController: NavController,
    viewmodel: ProfileViewModel = hiltViewModel(),
    qrCode: String,
) {
    val user = viewmodel.user.observeAsState().value


    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getUser(qrCode)
    }
    val coroutineScope = rememberCoroutineScope()

    when (user) {
        is Resource.Failure -> {
            failure(message = user.message) {
                viewmodel.getUser(qrCode)
            }
        }
        Resource.IsLoading -> {
            CircularProgressBox()
        }
        is Resource.Success -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colors.background)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {



                    if (user.result.profile != null) {
                        Image(
                            painter =
                            rememberImagePainter(BuildConfig.BASE_IMAGE+user.result.profile),
                            contentDescription = "login",
                            Modifier
                                .fillMaxWidth()
                                .height(310.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                         Box(modifier = Modifier
                             .fillMaxWidth()
                             .height(310.dp)
                             .background(MaterialTheme.colors.surface), contentAlignment = Alignment.Center) {
                             Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription =null, modifier = Modifier.size(80.dp))
                         }
                    }

                    Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(transBack)

                    ) {
                        Icon(
                            modifier = Modifier.mirror().align(Alignment.CenterStart).padding(MaterialTheme.spacing.default_margin)
                                .clickable {
                                           navController.navigateUp()
                                },

                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                        Icon(
                            modifier = Modifier.align(Alignment.CenterEnd).padding(MaterialTheme.spacing.default_margin).clickable {
                                navController.navigate(MainNavigation.UpdateUser.route+"?role_id=${user.result.roleId}&" +
                                        "userManage=${Gson().toJson(user.result)}",
                                )
                            },
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )

                    }


                    Card(
                        shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                        backgroundColor = MaterialTheme.colors.background,
                        elevation = 0.dp,
                        modifier = Modifier.padding(top = 280.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(MaterialTheme.spacing.default_margin)

                        ) {
                            VerticalDefaultMargin()

                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = user.result.name,
                                    fontSize = 24.sp,
                                    fontFamily = iranSansFamily,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                                if (user.result.roleId==UserTypes.Worker.role_id) {
                                    Button(
                                        modifier = Modifier.align(Alignment.CenterEnd),
                                        onClick = {
                                            navController.navigate(MainNavigation.AddReport.route + "?worker=${Gson().toJson(user.result)}")

                                        }, colors = ButtonDefaults.buttonColors(
                                            backgroundColor = MaterialTheme.colors.primaryVariant,
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(25.dp)
                                    ) {
                                        Text(text = stringResource(R.string.submit_report))
                                    }
                                }


                            }
                            VerticalDefaultMarginBigger()

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                DrawableText(
                                    text = stringResource(R.string.see_reports),
                                    icon = Icons.Default.Report
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    modifier = Modifier.mirror()
                                )
                            }
                            CustomDivider()

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                                    .clickable {
                                        val shareIntent = Intent()
                                        shareIntent.action = Intent.ACTION_SEND
                                        shareIntent.type="text/plain"
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://touska.com/${user.result.qrCode}");
                                        startActivity(context,shareIntent,null)
                                    }
                            ) {
                                DrawableText(
                                    text = stringResource(R.string.share_user),
                                    icon = Icons.Default.Share
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    modifier = Modifier.mirror()
                                )
                            }
                            CustomDivider()



                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                DrawableText(
                                    text = stringResource(id = R.string.email),
                                    icon = Icons.Default.Email
                                )
                                Text(text = user.result.email)
                            }
                            CustomDivider()
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                DrawableText(
                                    text = stringResource(id = R.string.mobile),
                                    icon = Icons.Default.Phone
                                )
                                Text(text = user.result.mobile)
                            }
                            CustomDivider()



                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(46.dp)
                            ) {
                                DrawableText(
                                    text = stringResource(R.string.user_type),
                                    icon = Icons.Default.Person2
                                )
                                Text(text = user.result.roleTitle)
                            }
                            CustomDivider()
                            if (user.result.roleId==UserTypes.Worker.role_id) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp)
                                ) {
                                    DrawableText(
                                        text = stringResource(R.string.contractor),
                                        icon = Icons.Default.Feed
                                    )
                                    user.result.contractorName?.let {
                                        Text(it)
                                    }?: kotlin.run {
                                        Text(stringResource(R.string.free_worker))
                                    }

                                }
                                CustomDivider()
                            }


                            user.result.contractType?.let {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp)
                                ) {
                                    DrawableText(
                                        text = stringResource(R.string.contract_type),
                                        icon = Icons.Default.Feed
                                    )
                                    Text(text = it)
                                }
                                CustomDivider()
                            }

                            user.result.postTitle?.let {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(46.dp)
                                ) {
                                    DrawableText(
                                        text = stringResource(R.string.post_title),
                                        icon = Icons.Default.Workspaces
                                    )
                                    Text(text = it)
                                }
                                CustomDivider()
                            }


                        }
                    }
                }

            }
        }


        else -> {

        }
    }


}











