package com.example.touska.screens.workerList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.CircleShape


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
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
import com.example.touska.screens.postScreen.PostViewModel
import com.example.touska.screens.usermanageInsideScreen.UserManageInsideViewModel
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.UserTypes
import com.google.gson.Gson


import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun workerListScreen(
    navController: NavController,
    viewmodel: UserManageInsideViewModel = hiltViewModel(),
    postViewModel: PostViewModel = hiltViewModel()
) {
    val users = viewmodel.users.observeAsState().value
    val posts = postViewModel.posts.observeAsState().value

    var searchQuery by remember {
        mutableStateOf(TextFieldValue(""))
    }


    val context = LocalContext.current

    var expandedPost by remember { mutableStateOf(false) }
    var selectedPostText by remember { mutableStateOf("") }

    var postId by remember {
        mutableStateOf(0)
    }


    LaunchedEffect(Unit) {
        viewmodel.getSpeceficUsers(UserTypes.Worker.role_id, searchQuery.text,0)
        postViewModel.getPosts()
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(topBar =  {
        CustomTopAppbar(title = stringResource(R.string.chose_worker), navController = navController)
    }) {

        when(posts) {
            is Resource.Failure -> {
                failure(message = posts.message) {
                    postViewModel.getPosts()
                }
            }
            Resource.IsLoading ->  {
                CircularProgressBox()
            }
            is Resource.Success -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(Modifier.padding(horizontal= MaterialTheme.spacing.small_margin)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                viewmodel.getSpeceficUsers(UserTypes.Worker.role_id, searchQuery.text,postId)
                            },

                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(56.dp)
                                .padding(
                                    top = MaterialTheme.spacing.small_margin,
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
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MaterialTheme.colors.surface,
                                focusedBorderColor = MaterialTheme.colors.secondary
                            )

                            )
                        Spacer(modifier = Modifier.width(4.dp))

                   //     post dropdownn
                        ExposedDropdownMenuBox(
                            modifier = Modifier.width(160.dp).height(56.dp),
                            expanded = expandedPost,
                            onExpandedChange = {
                                expandedPost = !expandedPost
                            }
                        ) {
                            OutlinedTextField(
                                modifier =Modifier.width(160.dp).height(56.dp),
                                readOnly = true,
                                value = selectedPostText,
                                onValueChange = { },
                                label = { Text(stringResource(R.string.post_type)) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expandedPost
                                    )
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = MaterialTheme.colors.surface,
                                    focusedBorderColor = MaterialTheme.colors.secondary,

                                    )
                            )
                            ExposedDropdownMenu(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.customColorsPalette.cardBack),
                                expanded = expandedPost,
                                onDismissRequest = {
                                    expandedPost = false
                                }
                            ) {
                                posts.result.forEach() { selectedPost ->
                                    DropdownMenuItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            selectedPostText = selectedPost.title
                                            postId = selectedPost.id
                                            expandedPost = false
                                            viewmodel.getSpeceficUsers(UserTypes.Worker.role_id,searchQuery.text,postId)
                                        }
                                    ) {
                                        Text(text = selectedPost.title)
                                    }
                                }
                            }
                        }

                    }

                    users?.let {
                        if (users.isEmpty()) {
                            empty(message = stringResource(R.string.no_user_in_this_section))
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(users.size) { position ->
                                    Card(
                                        backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clickable {
                                                navController.navigate(
                                                    MainNavigation.AddReport.route + "?worker=${Gson().toJson(users[position])}")
                                            }
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
                                                        .border(1.dp, MaterialTheme.colors.surface,
                                                            CircleShape)
                                                )
                                            }

                                            if (users[position].roleId == UserTypes.Worker.role_id) {
                                                VerticalSmallSpacer()
                                                CustomDivider()
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




                                        }
                                    }

                                }
                            }
                        }

                    }

                }
            }
            null -> {

            }
        }


    }





}











