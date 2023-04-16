package com.example.touska.screens.usermanageInsideScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape


import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.data.BuildConfig
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.navigation.MainNavigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.UserTypes

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson


import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun userManageInsideScreen(
    navController: NavController,
    viewmodel: UserManageInsideViewModel = hiltViewModel(),
    role_id: Int,
) {
    val users = viewmodel.users.observeAsState().value

    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }


    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getSpeceficUsers(role_id, searchQuery.text)
    }


    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewmodel.getSpeceficUsers(role_id, searchQuery.text)
            },

            Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.spacing.small_margin,
                    start = MaterialTheme.spacing.small_margin,
                    end = MaterialTheme.spacing.small_margin
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.name_email_mobile),
                    color = MaterialTheme.colors.surface,
                    fontSize = 12.sp
                )
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = null,
                    Modifier.size(16.dp),
                    tint = MaterialTheme.colors.surface
                )
            },
            singleLine = true,

            )

        users?.let {
            if (users.isEmpty()) {
                empty(message = stringResource(R.string.no_user_in_this_section))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(users.size) { position ->
                        Card(
                            backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {


                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column() {
                                        DrawableText(
                                            text = users[position].email,
                                            Icons.Default.Email
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        DrawableText(
                                            text = users[position].name,
                                            Icons.Default.Person
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        DrawableText(
                                            text = users[position].mobile,
                                            Icons.Default.Phone
                                        )

                                    }


                                    Image(
                                        painter =
                                        if (users[position].profile != null) {
                                            rememberImagePainter(BuildConfig.BASE_IMAGE + users[position].profile)
                                        } else {
                                            painterResource(id = R.drawable.ic_profile)

                                        },
                                        contentScale = ContentScale.FillBounds,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                            .border(1.dp, MaterialTheme.colors.surface)
                                    )
                                }

                                if (users[position].roleId == UserTypes.Worker.role_id) {
                                    VerticalSmallSpacer()
                                    customDivider()
                                    VerticalSmallSpacer()
                                    Row() {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = stringResource(R.string.contract_type),
                                                color = MaterialTheme.colors.surface,
                                                fontSize = 10.sp
                                            )
                                            Text(text = users[position].contractType ?: "")
                                        }
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = stringResource(R.string.post_title),
                                                color = MaterialTheme.colors.surface,
                                                fontSize = 10.sp
                                            )
                                            Text(text = users[position].postTitle ?: "")
                                        }


                                    }
                                }

                                //update Button
                                OutlinedButton(
                                    modifier = Modifier.align(Alignment.End),
                                    onClick = {
                                    navController.navigate(MainNavigation.UpdateUser.route+"?role_id=${users[position].roleId}&" +
                                            "userManage=${Gson().toJson(users[position])}",
                                        )
                                },
                                colors = ButtonDefaults.buttonColors(Color.Transparent)

                                ) {
                                    Text(text = "به روز رسانی")
                                }

                            }
                        }

                    }
                }
            }

        }

    }


}











