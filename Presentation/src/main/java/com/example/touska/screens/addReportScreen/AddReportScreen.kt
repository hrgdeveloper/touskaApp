package com.example.touska.screens.addReportScreen

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.text.KeyboardOptions


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.data.BuildConfig
import com.example.domain.models.UserManage
import com.example.domain.models.WorkingTime
import com.example.shared.Resource
import com.example.touska.R

import com.example.touska.components.*
import com.example.touska.navigation.Navigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.mirror

import com.google.gson.Gson


import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun addReportScreen(
    navController: NavController,
    viewmodel: ReportViewModel = hiltViewModel(),
    worker: String,
) {
    val needs = viewmodel.needs.observeAsState().value

    val worker = Gson().fromJson(worker, UserManage::class.java)

    val workingTimes = remember { mutableStateListOf<WorkingTime>() }

    var description by remember {
        mutableStateOf(TextFieldValue(""))
    }


    var blocId by remember {
        mutableStateOf(0)
    }
    var floorId by remember {
        mutableStateOf(0)
    }

    var unitId by remember {
        mutableStateOf(0)
    }

    var blocStep by remember {
        mutableStateOf(1)
    }


    var blocPosition by remember {
        mutableStateOf(0)
    }

    var floorPosition by remember {
        mutableStateOf(0)
    }

    var blocTitle by remember {
        mutableStateOf("")
    }


    var expandedActivity by remember { mutableStateOf(false) }
    var selectedActivityText by remember { mutableStateOf("") }
    var activityId by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current


    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    var sheetType by remember {
        mutableStateOf(0) // 0 for selecting blocs
    }


    //for custom time 
    var customStartTime by remember {
        mutableStateOf("")
    }
    var customEndTime by remember {
        mutableStateOf("")
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
        { _, mHour: Int, mMinute: Int ->
            if (isForStartTime) {
                customStartTime =
                    "${mHour.toString().padStart(2, '0')}:${mMinute.toString().padStart(2, '0')}:00"
            } else {
                customEndTime =
                    "${mHour.toString().padStart(2, '0')}:${mMinute.toString().padStart(2, '0')}:00"
            }

        }, mHour, mMinute, true
    )




    LaunchedEffect(Unit) {
        viewmodel.getReportNeeds(worker.id)
    }


    when (needs) {
        is Resource.Failure -> {
            failure(message = needs.message) {
                viewmodel.getReportNeeds(worker.id)
            }
        }
        Resource.IsLoading -> {
            CircularProgressBox()
        }
        is Resource.Success -> {
            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                sheetBackgroundColor = MaterialTheme.colors.background,
                sheetContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VerticalDefaultMargin()
                        Card(
                            Modifier
                                .width(60.dp)
                                .height(4.dp),
                            backgroundColor = MaterialTheme.colors.surface,
                        ) {
                        }
                        VerticalDefaultMarginBigger()

                        if (sheetType == 0) {
                            if (blocStep == 1) {
                                Text(text = stringResource(id = R.string.chose_block))
                            } else if (blocStep == 2) {
                                Text(text = stringResource(R.string.chose_floor))
                            } else if (blocStep == 3) {
                                Text(text = stringResource(R.string.chose_unit))
                            }
                            LazyColumn(modifier = Modifier
                                .fillMaxWidth(),
                                content = {
                                    if (blocStep == 1) {
                                        items(needs.result.blocs.size) { position ->
                                            Row(horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        if (needs.result.blocs[position].floors != null && needs.result.blocs[position].floors!!.isNotEmpty()) {
                                                            blocId = needs.result.blocs[position].id
                                                            blocPosition = position
                                                            blocStep = 2
                                                        } else {
                                                            blocId = needs.result.blocs[position].id
                                                            blocStep = 1
                                                            blocTitle =
                                                                needs.result.blocs[position].name
                                                            coroutineScope.launch {
                                                                sheetState.hide()
                                                            }
                                                        }
                                                    }
                                                    .padding(
                                                        vertical = 15.dp,
                                                        horizontal = MaterialTheme.spacing.default_margin
                                                    )) {
                                                Text(text = needs.result.blocs[position].name)
                                                Icon(
                                                    imageVector = Icons.Default.ChevronRight,
                                                    contentDescription = null,
                                                    modifier = Modifier.mirror()
                                                )
                                            }
                                        }
                                    } else if (blocStep == 2) {
                                        items(needs.result.blocs[blocPosition].floors!!.size) { position ->
                                            Row(horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        if (needs.result.blocs[blocPosition].floors!![position].unit != null
                                                            && needs.result.blocs[blocPosition].floors!![position].unit!!.isNotEmpty()
                                                        ) {
                                                            floorId =
                                                                needs.result.blocs[blocPosition].floors!![position].id
                                                            floorPosition = position
                                                            blocStep = 3


                                                        } else {
                                                            floorId =
                                                                needs.result.blocs[blocPosition].floors!![position].id
                                                            blocTitle =
                                                                needs.result.blocs[blocPosition].name + "-" +
                                                                        needs.result.blocs[blocPosition].floors!![position].name



                                                            coroutineScope.launch {
                                                                sheetState.hide()
                                                                blocStep = 1
                                                            }
                                                        }
                                                    }
                                                    .padding(
                                                        vertical = 15.dp,
                                                        horizontal = MaterialTheme.spacing.default_margin
                                                    )) {
                                                Text(text = needs.result.blocs[blocPosition].floors!![position].name)
                                                Icon(
                                                    imageVector = Icons.Default.ChevronRight,
                                                    contentDescription = null,
                                                    modifier = Modifier.mirror()
                                                )
                                            }
                                        }


                                    } else if (blocStep == 3) {
                                        items(needs.result.blocs[blocPosition].floors!![floorPosition].unit!!.size) { position ->
                                            Row(horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        unitId =
                                                            needs.result.blocs[blocPosition].floors!![floorPosition].unit!![position].id
                                                        blocTitle =
                                                            needs.result.blocs[blocPosition].name + "-" +
                                                                    needs.result.blocs[blocPosition].floors!![floorPosition].name + "-" +
                                                                    needs.result.blocs[blocPosition].floors!![floorPosition].unit!![position].name


                                                        coroutineScope.launch {
                                                            sheetState.hide()
                                                            blocStep = 1
                                                        }

                                                    }
                                                    .padding(
                                                        vertical = 15.dp,
                                                        horizontal = MaterialTheme.spacing.default_margin
                                                    )) {
                                                Text(text = needs.result.blocs[blocPosition].floors!![floorPosition].unit!![position].name)
                                                Icon(
                                                    imageVector = Icons.Default.ChevronRight,
                                                    contentDescription = null,
                                                    modifier = Modifier.mirror()
                                                )
                                            }
                                        }
                                    }

                                })

                        } else if (sheetType == 1) {
                            LazyColumn {
                                items(needs.result.workingTimes.size) {
                                    Card(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (workingTimes.size > 0) {
                                                val lastEndTimeString =
                                                    workingTimes[workingTimes.size - 1].endTime
                                                val currentStartTimeString =
                                                    needs.result.workingTimes[it].startTime
                                                val dateFormat = SimpleDateFormat(
                                                    "HH:mm:ss",
                                                    Locale.getDefault()
                                                )
                                                val lastEndTime =
                                                    dateFormat.parse(lastEndTimeString)
                                                val currentStartTime =
                                                    dateFormat.parse(currentStartTimeString)
                                                if (currentStartTime.before(lastEndTime)) {
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            context.getString(R.string.time_alert),
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                    return@clickable
                                                }
                                            }
                                            workingTimes.add(needs.result.workingTimes[it])
                                            coroutineScope.launch {
                                                sheetState.hide()
                                            }
                                        }
                                        .padding(MaterialTheme.spacing.small_margin),
                                        backgroundColor = MaterialTheme.customColorsPalette.cardBack
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(MaterialTheme.spacing.small_margin)
                                        ) {
                                            Text(text = needs.result.workingTimes[it].title)
                                            VerticalSmallSpacer()
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceAround,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.start_time),
                                                    color = MaterialTheme.colors.surface,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text = stringResource(id = R.string.end_time),
                                                    color = MaterialTheme.colors.surface,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }

                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = needs.result.workingTimes[it].startTime,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text = needs.result.workingTimes[it].endTime,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }

                                        }

                                    }
                                }
                            }


                        } else if (sheetType == 2) {
                            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default_margin)) {
                                Row() {
                                    Box(
                                        modifier = Modifier
                                            .height(48.dp)
                                            .weight(1f)
                                            .clip(RoundedCornerShape(4.dp))
                                            .border(
                                                1.dp, MaterialTheme.colors.surface,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                isForStartTime = true
                                                mTimePickerDialog.show()
                                            },
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        DrawableText(
                                            text =
                                            if (customStartTime.isEmpty()) {
                                                stringResource(id = R.string.start_time)
                                            } else {
                                                customStartTime
                                            }, painterResource(id = R.drawable.ic_clock)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Box(
                                        modifier = Modifier
                                            .height(48.dp)
                                            .weight(1f)
                                            .clip(RoundedCornerShape(4.dp))
                                            .border(
                                                1.dp, MaterialTheme.colors.surface,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable {
                                                isForStartTime = false
                                                mTimePickerDialog.show()
                                            },
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        DrawableText(
                                            text =
                                            if (customEndTime.isEmpty()) {
                                                stringResource(id = R.string.end_time)
                                            } else {
                                                customEndTime
                                            }, painterResource(id = R.drawable.ic_clock)
                                        )
                                    }

                                }
                                VerticalDefaultMargin()
                                Button(
                                    onClick = {
                                        if (customStartTime.isEmpty()) {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.insert_start_time_title),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }
                                        if (customEndTime.isEmpty()) {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.insert_end_time_title),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }
                                        if (workingTimes.size > 0) {
                                            val lastEndTimeString =
                                                workingTimes[workingTimes.size - 1].endTime
                                            val dateFormat = SimpleDateFormat(
                                                "HH:mm:ss",
                                                Locale.getDefault()
                                            )
                                            val lastEndTime =
                                                dateFormat.parse(lastEndTimeString)
                                            val currentStartTime =
                                                dateFormat.parse(customStartTime)

                                            if (currentStartTime.before(lastEndTime)) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        context.getString(R.string.time_alert),
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                                return@Button
                                            }
                                        }
                                        workingTimes.add(
                                            WorkingTime(
                                                0,
                                                context.getString(R.string.custom),
                                                customStartTime,
                                                customEndTime
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetState.hide()
                                            customStartTime=""
                                            customEndTime=""
                                        }
                                    }, modifier = Modifier
                                        .height(48.dp)
                                        .fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor =
                                        MaterialTheme.colors.primaryVariant
                                    )
                                ) {
                                    Text(text = stringResource(id = R.string.add))
                                }

                            }

                        }

                        VerticalDefaultMarginBigger()
                    }


                }

            ) {
                Scaffold(topBar = {
                    CustomTopAppbar(
                        title = stringResource(id = R.string.add_new_report),
                        navController = navController
                    )
                }) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .weight(1f)
                                .padding(horizontal = MaterialTheme.spacing.default_margin),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Spacer(modifier = Modifier.height(30.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val painer = worker.profile?.let {
                                    rememberImagePainter(BuildConfig.BASE_IMAGE + worker.profile)
                                } ?: kotlin.run {
                                    painterResource(id = R.drawable.ic_profile)
                                }

                                Image(
                                    painter = painer, contentDescription = null,
                                    Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colors.surface, CircleShape),
                                    contentScale = ContentScale.FillBounds

                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = worker.name,
                                    fontSize = 24.sp,
                                    fontFamily = iranSansFamily,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            //profile picture

                            VerticalDefaultMargin()
                            //contracts types
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = expandedActivity,
                                onExpandedChange = {
                                    expandedActivity = !expandedActivity
                                }
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    readOnly = true,
                                    value = selectedActivityText,
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.activity_name)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedActivity
                                        )
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_worker),
                                            contentDescription = null
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
                                    expanded = expandedActivity,
                                    onDismissRequest = {
                                        expandedActivity = false
                                    }
                                ) {
                                    needs.result.activities.forEach { selectedActivity ->
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),

                                            onClick = {
                                                selectedActivityText = selectedActivity.title
                                                activityId = selectedActivity.id
                                                expandedActivity = false
                                            }
                                        ) {
                                            Text(text = selectedActivity.title)
                                        }
                                    }
                                }
                            }
                            VerticalDefaultMargin()

                            //select Bloc
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp, MaterialTheme.colors.surface,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    coroutineScope.launch {
                                        sheetType = 0
                                        sheetState.show()
                                    }
                                }
                                .padding(horizontal = MaterialTheme.spacing.default_margin),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DrawableText(
                                    text =
                                    if (blocTitle.isEmpty()) {
                                        stringResource(R.string.chose_block)
                                    } else {
                                        blocTitle
                                    },
                                    painterResource(id = R.drawable.ic_blocs)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }

                            VerticalDefaultMargin()

                            //select WorkingTime
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp, MaterialTheme.colors.surface,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        sheetType = 1
                                        coroutineScope.launch {
                                            sheetState.show()
                                        }
                                    }
                                    .padding(
                                        horizontal = MaterialTheme.spacing.default_margin,
                                        vertical = 15.dp
                                    ),

                                ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    DrawableText(
                                        text = stringResource(R.string.add_working_time),
                                        painterResource(id = R.drawable.ic_add_time)
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            sheetType = 2
                                            coroutineScope.launch {
                                                sheetState.show()
                                            }
                                        }
                                    )
                                }

                                if (workingTimes.size > 0) {
                                    VerticalSmallSpacer()
                                    CustomDivider()
                                    VerticalSmallSpacer()

                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        workingTimes.forEachIndexed { index, workingTime ->
                                            Row() {
                                                Text(
                                                    text = stringResource(id = R.string.start_time),
                                                    modifier = Modifier.weight(1f),
                                                    color = MaterialTheme.colors.surface,
                                                    fontSize = 12.sp
                                                )
                                                Text(
                                                    text = stringResource(id = R.string.end_time),
                                                    modifier = Modifier.weight(1f),
                                                    color = MaterialTheme.colors.surface,
                                                    fontSize = 12.sp
                                                )
                                            }

                                            Row() {
                                                Text(
                                                    text = workingTime.startTime,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Text(
                                                    text = workingTime.endTime,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .clickable {
                                                            workingTimes.remove(workingTime)
                                                        },
                                                    tint = Color.Red
                                                )
                                            }
                                        }
                                    }

                                }
                            }




                            VerticalDefaultMargin()


                            //name text field
                            OutlinedTextField(
                                value = description,
                                onValueChange = {
                                    description = it
                                },

                                Modifier
                                    .fillMaxWidth(),
                                label = {
                                    Text(text = stringResource(R.string.descriptions))
                                },
                                singleLine = false,
                                minLines = 5,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = MaterialTheme.colors.surface,
                                    focusedBorderColor = MaterialTheme.colors.secondary
                                ),
                            )

                            VerticalDefaultMargin()


                        }
                        Box(
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.small_margin,
                                vertical = MaterialTheme.spacing.default_margin
                            )
                        ) {
                            ConfirmButton(onclick = {


                            }) {
//                            if (register is Resource.IsLoading) {
//                                CircularProgressIndicator(
//                                    color = Color.White,
//                                    modifier = Modifier.size(24.dp)
//                                )
//                            } else {
                                Text(text = stringResource(id = R.string.submit_report))
                                //    }

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












