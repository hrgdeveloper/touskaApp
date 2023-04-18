package com.example.touska.screens.workingTimeScreen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun workingTimeScreen(
    navController: NavController,
    viewmodel: WorkingTimeViewModel = hiltViewModel(),

    ) {
    val workingTimes = viewmodel.workingTimes.observeAsState().value
    val addWorkingTime = viewmodel.addWorkingTime.observeAsState().value
    val updateWorkingTime = viewmodel.updateWorkingTime.observeAsState().value
    val deleteWorkingTime = viewmodel.deleteWorkingTime.observeAsState().value


    var isUpdate by remember {
        mutableStateOf(false)
    }


    var workingTimeTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var startTime by remember {
        mutableStateOf("")
    }
    var endTime by remember {
        mutableStateOf("")
    }


    var workingTimeIdForUpdate by remember {
        mutableStateOf(0)
    }
    var workingTimeIdForDelete by remember {
        mutableStateOf(0)
    }


    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getWorkingTimes()
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addWorkingTime) {
        when (addWorkingTime) {
            is Resource.Failure -> {
                addWorkingTime.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                workingTimeTitle = TextFieldValue("")
                startTime = ""
                endTime = ""
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updateWorkingTime) {
        when (updateWorkingTime) {
            is Resource.Failure -> {
                updateWorkingTime.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                workingTimeTitle = TextFieldValue("")
                startTime = ""
                endTime = ""
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deleteWorkingTime) {
        when (deleteWorkingTime) {
            is Resource.Failure -> {
                deleteWorkingTime.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.working_time_deleted).toastLong(context)
            }
            null -> {
            }
        }
    }

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    var isForStartTime by remember {
        mutableStateOf(true)
    }

    //this variable define if we click on setStrat Time or End Time
    // Creating a TimePicker dialod
    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            if (isForStartTime) {
                startTime = "${mHour.toString().padStart(2,'0')}:${mMinute.toString().padStart(2,'0')}"
            }else {
                endTime = "${mHour.toString().padStart(2,'0')}:${mMinute.toString().padStart(2,'0')}"
            }

        }, mHour, mMinute, true
    )

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetBackgroundColor = MaterialTheme.colors.background,
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
                        stringResource(R.string.update_working_time)
                    } else {
                        stringResource(R.string.add_new_working_time)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = workingTimeTitle,
                    onValueChange = {
                        workingTimeTitle = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.working_timne_title))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                    ),
                )
                VerticalDefaultMargin()


                Card(
                    backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = MaterialTheme.spacing.default_margin)

                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            isForStartTime=true
                            mTimePickerDialog.show()

                        }, contentAlignment = Alignment.Center) {
                        if (startTime.isNotEmpty()) {
                            Text(
                                text = startTime,
                                fontSize = 14.sp,
                                fontFamily = iranSansFamily,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            Row() {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_time),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.surface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.chose_start_time),
                                    fontSize = 16.sp,
                                    fontFamily = iranSansFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }


                        }
                    }


                }

                VerticalDefaultMargin()

                Card(
                    backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            isForStartTime=false
                            mTimePickerDialog.show()

                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                ) {

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        if (endTime.isNotEmpty()) {
                            Text(
                                text = endTime,
                                fontSize = 14.sp,
                                fontFamily = iranSansFamily,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            Row() {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_time),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.surface
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = stringResource(R.string.chose_end_time),
                                    fontSize = 16.sp,
                                    fontFamily = iranSansFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                }

                VerticalDefaultMargin()


                ConfirmButton(onclick = {
                    if (isUpdate) {
                        viewmodel.updateWorkingTime(
                            workingTimeTitle.text,
                            startTime,
                            endTime,
                            workingTimeIdForUpdate,
                        )
                    } else {
                        viewmodel.createWorkingTime(workingTimeTitle.text, startTime, endTime)
                    }
                }
                ) {
                    if (addWorkingTime is Resource.IsLoading) {
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
                CustomTopAppbar(
                    title = stringResource(R.string.manage_working_times),
                    navController = navController
                )
            }

        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when (workingTimes) {
                        is Resource.Failure -> {
                            failure(message = workingTimes.message) {
                                viewmodel.getWorkingTimes()
                            }
                        }
                        Resource.IsLoading -> {
                            CircularProgressBox()
                        }
                        is Resource.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(workingTimes is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getWorkingTimes()
                                }) {
                                if (workingTimes.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_working_times))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(workingTimes.result.size) { position ->
                                                Column {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(MaterialTheme.spacing.default_margin),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        Row() {
                                                            Text(
                                                                text = workingTimes.result[position].title,
                                                                fontFamily = iranSansFamily,
                                                                fontWeight = FontWeight.Medium
                                                            )
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
                                                                            workingTimeTitle =
                                                                                TextFieldValue(
                                                                                    workingTimes.result[position].title
                                                                                )
                                                                            startTime =
                                                                                workingTimes.result[position].startTime
                                                                            endTime =
                                                                                workingTimes.result[position].endTime
                                                                            workingTimeIdForUpdate =
                                                                                workingTimes.result[position].id
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
                                                                        workingTimeIdForDelete =
                                                                            workingTimes.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )


                                                        }
                                                    }

                                                    VerticalSmallSpacer()

                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(horizontal = MaterialTheme.spacing.default_margin),
                                                    ) {
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = "ساعت شروع  ",
                                                                color = MaterialTheme.colors.surface
                                                            )
                                                            Text(
                                                                workingTimes.result[position].startTime,
                                                                fontSize = 16.sp
                                                            )
                                                        }
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = "ساعت پایان  ",
                                                                color = MaterialTheme.colors.surface
                                                            )
                                                            Text(
                                                                workingTimes.result[position].endTime,
                                                                fontSize = 16.sp
                                                            )
                                                        }

                                                    }

                                                    if (position != workingTimes.result.size - 1) {
                                                        CustomDivider()
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
                            workingTimeTitle = TextFieldValue("")
                            startTime = ""
                            endTime = ""
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_working_time))
                    })
                    VerticalDefaultMargin()
                }
            }
        }


    }

    if (openDeleteDialog.value) {
        CustomAlertDialog(
            onDismiss = { openDeleteDialog.value = false },
            title = stringResource(id = R.string.delete_working_time_title),
            text = stringResource(id = R.string.delete_working_time),
            onConfirmButton = {
                viewmodel.deleteWorkingTime(workingTimeIdForDelete)
            },
        )
    }


}







