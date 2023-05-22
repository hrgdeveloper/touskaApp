package com.example.touska.screens.reportScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.models.Report
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.navigation.MainNavigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.FilterModel
import com.example.touska.utils.FilterTypes
import com.example.touska.utils.returnProperMessage
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun reportScreen(
    navController: NavController,
    viewmodel: ReportViewModel = hiltViewModel()
) {
    val needs = viewmodel.reportsNeeds.observeAsState().value

    val reports = viewmodel.reports.observeAsState().value
    val context = LocalContext.current

    var filterType by remember {
        mutableStateOf(FilterTypes.BLOCK.type)
    }




    LaunchedEffect(Unit) {
        viewmodel.fetchReports()
    }

    LaunchedEffect(Unit) {
        viewmodel.fetchReportNeeds()
    }


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val sheetStateSelect = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )

    val coroutineScope = rememberCoroutineScope()


    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }


    val cellWidth: (Int) -> Dp = { index ->
        when (index) {
            1 -> 150.dp
            2 -> 80.dp
            3 -> 80.dp
            4 -> 80.dp
            5 -> 80.dp
            6 -> 100.dp
            7 -> 100.dp
            8 -> 150.dp
            9 -> 150.dp
            10->100.dp
            else -> 100.dp
        }
    }
    val headerCellTitle: @Composable (Int) -> Unit = { index ->
        val value = when (index) {
            1 -> stringResource(R.string.worker_name)
            2 -> stringResource(R.string.block)
            3 -> stringResource(R.string.floor)
            4 -> stringResource(R.string.unit)
            5 -> stringResource(R.string.post)
            6 -> stringResource(R.string.activity_type)
            7 -> stringResource(R.string.total_working_time)
            8 -> stringResource(R.string.supervisor_name)
            9 -> stringResource(R.string.contractor)
            10 -> stringResource(R.string.date_submited)

            else -> ""
        }
        Text(
            text = value,
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 15.sp,
            modifier = Modifier
                .background(MaterialTheme.customColorsPalette.cardBack)
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .width(cellWidth(index)),
            overflow = TextOverflow.Ellipsis,
            fontFamily = iranSansFamily, fontWeight = FontWeight.Medium
        )
    }
    val cellText: @Composable (Int, Report) -> Unit = { index, report ->
        val value = when (index) {
            1 -> report.workerName
            2 -> report.blockName
            3 -> report.floorName ?: " - "
            4 -> report.unitName ?: " - "
            5 -> report.post
            6 -> report.activity
            7 -> report.totalTime
            8 -> report.supervisorName ?: ""
            9 -> report.contractorName ?: stringResource(id = R.string.free_worker)
            10 -> report.submitted
            else -> ""
        }

        Text(
            text = value,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }



    ModalBottomSheetLayout(
        sheetState = sheetStateSelect,
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
                VerticalDefaultMargin()
                if (needs is Resource.Success) {
                    if (filterType == FilterTypes.BLOCK.type) {
                        LazyColumn {
                            items(needs.result.blocs) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.blockId.value = it.id
                                        viewmodel.blockName.value = it.name
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.block_filter,
                                                FilterTypes.BLOCK.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.name)
                                }
                                CustomDivider()
                            }
                        }
                    } else if (filterType == FilterTypes.FLOOR.type) {
                        LazyColumn {
                            items(needs.result.floors) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.floorId.value = it.id
                                        viewmodel.floorName.value = it.name
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.floor_filter,
                                                FilterTypes.FLOOR.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.name)
                                }
                                CustomDivider()
                            }
                        }
                    } else if (filterType == FilterTypes.UNIT.type) {
                        LazyColumn {
                            items(needs.result.units) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.unitId.value = it.id
                                        viewmodel.unitName.value = it.name
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.unit_filter,
                                                FilterTypes.UNIT.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.name)
                                }
                                CustomDivider()
                            }
                        }
                    } else if (filterType == FilterTypes.POST.type) {
                        LazyColumn {
                            items(needs.result.posts) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.postId.value = it.id
                                        viewmodel.postTitle.value = it.title
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.post_filter,
                                                FilterTypes.POST.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.title)
                                }
                                CustomDivider()
                            }
                        }
                    } else if (filterType == FilterTypes.ACTIVITY.type) {
                        LazyColumn {
                            items(needs.result.activities) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.activityId.value = it.id
                                        viewmodel.activityName.value = it.title
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.post_filter,
                                                FilterTypes.ACTIVITY.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.title)
                                }
                                CustomDivider()
                            }
                        }
                    } else if (filterType == FilterTypes.CONTRACT.type) {
                        LazyColumn {
                            items(needs.result.contracts) {
                                Surface(
                                    selected = false, onClick = {
                                        viewmodel.contractTypeId.value = it.id
                                        viewmodel.contractTypeName.value = it.title
                                        viewmodel.fetchReports()
                                        viewmodel.addToFilterList(
                                            FilterModel(
                                                R.string.contract_filter,
                                                FilterTypes.CONTRACT.type
                                            )
                                        )
                                        coroutineScope.launch {
                                            sheetStateSelect.hide()
                                        }
                                    }, color = Color.Transparent, modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                                ) {
                                    Text(text = it.title)
                                }
                                CustomDivider()
                            }
                        }
                    }
                }

                VerticalDefaultMargin()

            }
        },
        modifier = Modifier
            .fillMaxSize(),

        ) {
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
                    if (needs is Resource.Success) {
                        VerticalDefaultMargin()
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal =
                                MaterialTheme.spacing.default_margin
                            )) {
                            Text(modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth() ,
                                text = if (viewmodel.startDatePersian.value.isEmpty())  " " else  stringResource(id = R.string.startDate),
                                color = MaterialTheme.colors.surface)
                            Text(modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                                text = if (viewmodel.endDatePersian.value.isEmpty())  " " else stringResource(id = R.string.endDate),
                                color = MaterialTheme.colors.surface)
                        }

                        Row(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default_margin)) {
                            Box(modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.customColorsPalette.cardBack,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = MaterialTheme.spacing.small_margin)
                                .clickable {
                                    showDatePicker(true, context, viewmodel)
                                }, contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (viewmodel.startDatePersian.value.isEmpty()) {
                                        stringResource(id = R.string.startDate)
                                    } else {
                                        viewmodel.startDatePersian.value
                                    },
                                    style = TextStyle(
                                        color = if (viewmodel.startDatePersian.value.isEmpty()) MaterialTheme.colors.surface else
                                            MaterialTheme.colors.primary,
                                        fontFamily = iranSansFamily
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(modifier = Modifier
                                .height(50.dp)
                                .weight(1f)
                                .clip(
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.customColorsPalette.cardBack,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    showDatePicker(false, context, viewmodel)
                                }
                                .padding(horizontal = MaterialTheme.spacing.small_margin),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (viewmodel.endDatePersian.value.isEmpty()) {
                                        stringResource(id = R.string.endDate)
                                    } else {
                                        viewmodel.endDatePersian.value
                                    },
                                    style = TextStyle(
                                        color = if (viewmodel.endDatePersian.value.isEmpty()) MaterialTheme.colors.surface else
                                            MaterialTheme.colors.primary,
                                        fontFamily = iranSansFamily
                                    )
                                )
                            }

                        }


                        VerticalDefaultMargin()
                        FilterBox(title = R.string.block_filter, text = viewmodel.blockName.value) {
                            coroutineScope.launch {
                                filterType = FilterTypes.BLOCK.type
                                sheetStateSelect.show()

                            }
                        }
                        VerticalDefaultMargin()
                        FilterBox(title = R.string.floor_filter, text = viewmodel.floorName.value) {
                            coroutineScope.launch {
                                filterType = FilterTypes.FLOOR.type
                                sheetStateSelect.show()

                            }
                        }
                        VerticalDefaultMargin()
                        FilterBox(title = R.string.unit_filter, text = viewmodel.unitName.value) {
                            coroutineScope.launch {
                                filterType = FilterTypes.UNIT.type
                                sheetStateSelect.show()

                            }
                        }

                        VerticalDefaultMargin()
                        FilterBox(title = R.string.post_filter, text = viewmodel.postTitle.value) {
                            coroutineScope.launch {
                                filterType = FilterTypes.POST.type
                                sheetStateSelect.show()

                            }
                        }


                        VerticalDefaultMargin()
                        FilterBox(
                            title = R.string.activity_filter,
                            text = viewmodel.activityName.value
                        ) {
                            coroutineScope.launch {
                                filterType = FilterTypes.ACTIVITY.type
                                sheetStateSelect.show()

                            }
                        }

                        VerticalDefaultMargin()
                        FilterBox(
                            title = R.string.contract_filter,
                            text = viewmodel.contractTypeName.value
                        ) {
                            coroutineScope.launch {
                                filterType = FilterTypes.CONTRACT.type
                                sheetStateSelect.show()

                            }
                        }



                    }

                }

            },
            modifier = Modifier
                .fillMaxSize(),

            ) {
            when (reports) {
                is Resource.Failure -> {
                    failure(message = reports.returnProperMessage(context)) {
                        viewmodel.fetchReports()
                    }
                }
                Resource.IsLoading -> {
                    CircularProgressBox()
                }
                is Resource.Success -> {
                    Scaffold(topBar = {
                        TopAppBar(backgroundColor = MaterialTheme.customColorsPalette.top_bar) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Icon(imageVector = Icons.Default.FilterAlt,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .clickable {
                                            coroutineScope.launch {
                                                sheetState.show()
                                            }
                                        }
                                )
                                OutlinedButton(
                                    onClick = {
                                        navController.navigate(MainNavigation.WorkerList.route)
                                    },
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    shape = RoundedCornerShape(25.dp),
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colors.primaryVariant
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = MaterialTheme.colors.primaryVariant,
                                        backgroundColor = Color.Transparent
                                    ),

                                    ) {
                                    Text(
                                        text = stringResource(id = R.string.add_new_report),
                                    )
                                }

                            }
                        }
                    }) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyRow(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
                                items(viewmodel.filtersList) {
                                    Card(
                                        backgroundColor = MaterialTheme.customColorsPalette.cardBack,
                                        modifier = Modifier.clickable {
                                            viewmodel.removeFromFilterList(it)
                                            viewmodel.fetchReports()
                                        },
                                        shape = RoundedCornerShape(25.dp)

                                    ) {
                                        Row(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = stringResource(id = it.stringRes))
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = null,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }

                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                VerticalSmallSpacer()
                                Table(
                                    columnCount = 11,
                                    cellWidth = cellWidth,
                                    data = reports.result,
                                    modifier = Modifier.verticalScroll(rememberScrollState()),
                                    headerCellContent = headerCellTitle,
                                    cellContent = cellText
                                )

                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f),
                            ) {

                            }

                        }
                    }


                }
                null -> {

                }
            }
        }


    }


}

fun showDatePicker(isStart: Boolean, context: Context, viewmodel: ReportViewModel) {
    val picker = PersianDatePickerDialog(context)
        .setPositiveButtonString(context.resources.getString(R.string.confirm))
        .setNegativeButton(context.resources.getString(R.string.cancel))
        .setTodayButton(context.resources.getString(R.string.today))
        .setTodayButtonVisible(true)
        .setMinYear(1402)
        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
        .setShowInBottomSheet(true)
        .setListener(object : PersianPickerListener {
            override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                val gregorianDate = persianPickerDate.gregorianYear.toString() + "-" +
                        persianPickerDate.gregorianMonth + "-" + persianPickerDate.gregorianDay
                val persianDate = persianPickerDate.persianYear.toString() + "-" +
                        persianPickerDate.persianMonth + "-" + persianPickerDate.persianDay

                if (isStart) {
                    viewmodel.startDate.value = gregorianDate
                    viewmodel.startDatePersian.value = persianDate
                    viewmodel.addToFilterList(FilterModel(R.string.start_time_filter,FilterTypes.START_TIME.type))
                } else {
                    viewmodel.endDate.value = gregorianDate
                    viewmodel.endDatePersian.value = persianDate
                    viewmodel.addToFilterList(FilterModel(R.string.end_time_filter,FilterTypes.END_TIME.type))
                }

                viewmodel.fetchReports()
            }

            override fun onDismissed() {

            }

        })
    picker.show()
}


@Composable
fun <T> Table(
    columnCount: Int,
    cellWidth: (index: Int) -> Dp,
    data: List<T>,
    modifier: Modifier = Modifier,
    headerCellContent: @Composable (index: Int) -> Unit,
    cellContent: @Composable (index: Int, item: T) -> Unit,
) {
    Surface(
        modifier = modifier.background(Color.Transparent),
        color = Color.Transparent
    ) {
        LazyRow(
            modifier = Modifier.padding(4.dp)
        ) {
            items((0 until columnCount).toList()) { columnIndex ->
                if (columnIndex == 0) {
                    //show number column at index 0
                    Column(
                        modifier = Modifier
                            .width(30.dp)
                            .padding(top = 34.dp)
                            .border(1.dp, MaterialTheme.customColorsPalette.divider_color)
                            .background(MaterialTheme.customColorsPalette.cardBack),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for (i in data.indices) {
                            Text(
                                text = (i + 1).toString(),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            CustomDivider()
                        }
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .width(cellWidth(columnIndex))
                            .border(1.dp, MaterialTheme.customColorsPalette.divider_color),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        (0..data.size).forEach { index ->
                            if (index == 0) {
                                headerCellContent(columnIndex)

                            } else {
                                cellContent(columnIndex, data[index - 1])
                            }

                            Divider(
                                color = MaterialTheme.customColorsPalette.divider_color,
                                thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth(1f)
                            )
                        }
                    }
                    CustomDivider()
                }
            }
        }
    }
}
