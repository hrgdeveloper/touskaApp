package com.example.touska.screens.activityScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.touska.navigation.MainNavigation
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
fun activityScreen(
    navController: NavController,
    viewmodel: ActivityviewModel = hiltViewModel(),
    postId: Int,
    postTitle:String
) {
    val activities = viewmodel.activities.observeAsState().value
    val addActivity = viewmodel.addActivity.observeAsState().value
    val updateActivity = viewmodel.updateActivity.observeAsState().value
    val deleteActivity = viewmodel.deleteActivity.observeAsState().value



    var isUpdate by remember {
        mutableStateOf(false)
    }

    var isAdd by remember {
        mutableStateOf(false)
    }

    var activityTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }


    var activityIdForUpdate by remember {
        mutableStateOf(0)
    }
    var activityIdForDelete by remember {
        mutableStateOf(0)
    }



    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.getActivities(postId)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addActivity) {
        when (addActivity) {
            is Resource.Failure -> {
                addActivity.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                activityTitle = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updateActivity) {
        when (updateActivity) {
            is Resource.Failure -> {
                updateActivity.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                activityTitle = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deleteActivity) {
        when (deleteActivity) {
            is Resource.Failure -> {
                deleteActivity.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.activity_deleted_suucessfully).toastLong(context)
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
                        stringResource(R.string.updateActivity)
                    } else {
                        stringResource(R.string.add_new_activity)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = activityTitle,
                    onValueChange = {
                        activityTitle = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.activity_name))
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
                        viewmodel.updateActivity(
                            activityTitle.text,
                            activityIdForUpdate,
                        )
                    } else {
                        viewmodel.createActivity( activityTitle.text,postId)
                    }
                }
                ) {
                    if (addActivity is Resource.IsLoading) {
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
            CustomTopAppbar(title = postTitle, navController = navController)
            }
            
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when(activities) {
                        is Resource.Failure -> {
                            failure(message = activities.message) {
                                viewmodel.getActivities(postId)
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()
                        }
                        is Resource.Success ->  {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(activities is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getActivities(postId)
                                }) {
                                if (activities.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_activities))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(activities.result.size) { position ->
                                                Column() {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(MaterialTheme.spacing.default_margin),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        Row() {
                                                            Text(text = activities.result[position].title)
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
                                                                            activityTitle =
                                                                                TextFieldValue(
                                                                                    activities.result[position].title
                                                                                )

                                                                            activityIdForUpdate =
                                                                                activities.result[position].id
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
                                                                        activityIdForDelete =
                                                                            activities.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )



                                                        }
                                                    }

                                                    if (position != activities.result.size - 1) {
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
                            activityTitle = TextFieldValue("")
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_activity))
                    })
                    VerticalDefaultMargin()
                }
            }
        }
        
   
    }

    if (openDeleteDialog.value) {
        CustomAlertDialog(
            onDismiss = { openDeleteDialog.value = false },
            title = stringResource(id = R.string.delete_activity_title),
            text = stringResource(id = R.string.delete_activity),
            onConfirmButton = {
                viewmodel.deleteActivity(activityIdForDelete)
            },
        )
    }




}







