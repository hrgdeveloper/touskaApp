package com.example.touska.screens.postScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.mirror
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun postScreen(
    navController: NavController,
    viewmodel: PostViewModel = hiltViewModel(),

) {
    val posts = viewmodel.posts.observeAsState().value
    val addPost = viewmodel.addPost.observeAsState().value
    val updatePost = viewmodel.updatePost.observeAsState().value
    val deletePost = viewmodel.deletePost.observeAsState().value



    var isUpdate by remember {
        mutableStateOf(false)
    }

    var isAddUnit by remember {
        mutableStateOf(false)
    }


    var postTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }


    var postIdForUpdate by remember {
        mutableStateOf(0)
    }
    var postIdForDelete by remember {
        mutableStateOf(0)
    }



    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.getPosts()
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addPost) {
        when (addPost) {
            is Resource.Failure -> {
                addPost.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                postTitle = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updatePost) {
        when (updatePost) {
            is Resource.Failure -> {
                updatePost.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                postTitle = TextFieldValue("")

            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deletePost) {
        when (deletePost) {
            is Resource.Failure -> {
                deletePost.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.post_deleted_suucessfully).toastLong(context)
            }
            null -> {
            }
        }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetBackgroundColor = MaterialTheme.customColorsPalette.cardBack,
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                VerticalDefaultMargin()
                Card(
                    Modifier
                        .width(60.dp)
                        .height(4.dp),
                    backgroundColor = MaterialTheme.colors.surface,
                ) {
                }
                VerticalSmallSpacer()

                Text(
                    text = if (isUpdate) {
                        stringResource(R.string.updatePost)
                    } else {
                        stringResource(R.string.add_new_post)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = postTitle,
                    onValueChange = {
                        postTitle = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.post_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                    ),
                )
                VerticalDefaultMargin()



                ConfirmButton(onclick = {
                    if (isUpdate) {
                        viewmodel.updatePost(
                            postTitle.text,
                            postIdForUpdate
                        )
                    } else {
                        viewmodel.createPost(postTitle.text)
                    }
                }
                ) {
                    if (addPost is Resource.IsLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        if (isUpdate) {
                            Text(text = stringResource(R.string.update))
                        } else {
                            Text(text = stringResource(R.string.add))
                        }
                    }
                }
                VerticalDefaultMargin()


            }
        },
        modifier = Modifier
            .fillMaxSize(),

        ) {
        Scaffold(
            topBar = {
            CustomTopAppbar(title = stringResource(R.string.post_manage),
                navController = navController)
            }
            
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when(posts) {
                        is Resource.Failure -> {
                            failure(message = posts.message) {
                                viewmodel.getPosts()
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()
                        }
                        is Resource.Success ->  {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(posts is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getPosts()
                                }) {
                                if (posts.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_posts))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(posts.result.size) { position ->
                                                Column() {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {

                                                            }
                                                            .padding(MaterialTheme.spacing.default_margin),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        Row() {
                                                            Text(text = posts.result[position].title)
                                                        }

                                                        Row() {
                                                            Icon(
                                                                imageVector = Icons.Default.Edit,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(start = MaterialTheme.spacing.small_margin)
                                                                    .clickable {
                                                                        coroutineScope.launch {
                                                                            isUpdate = true
                                                                            sheetState.show()
                                                                            postTitle =
                                                                                TextFieldValue(
                                                                                    posts.result[position].title
                                                                                )
                                                                            postIdForUpdate =
                                                                                posts.result[position].id
                                                                        }
                                                                    },
                                                            )

                                                            Icon(
                                                                imageVector = Icons.Default.Delete,
                                                                contentDescription = null,
                                                                modifier = Modifier
                                                                    .padding(start = MaterialTheme.spacing.small_margin)
                                                                    .clickable {
                                                                        openDeleteDialog.value =
                                                                            true
                                                                        postIdForDelete =
                                                                            posts.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )



                                                        }
                                                    }

                                                    if (position != posts.result.size - 1) {
                                                        customDivider()
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ConfirmButton(onclick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) {
                                sheetState.hide()
                            } else {
                                sheetState.show()
                            }
                            postTitle = TextFieldValue("")
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_post))
                    })
                    VerticalDefaultMargin()
                }
            }
        }
        
   
    }

    if (openDeleteDialog.value) {
        CustomAlertDialog(
            onDismiss = { openDeleteDialog.value = false },
            title = stringResource(id = R.string.delete_unit),
            text = stringResource(id = R.string.confirm_delete_unit),
            onConfirmButton = {
                viewmodel.deletePost(postIdForDelete)
            },
        )
    }




}







