package com.example.touska.screens.reportScreen

import android.annotation.SuppressLint
import android.widget.TableLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import com.example.touska.utils.mirror
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

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
            0 -> 150.dp
            1 -> 80.dp
            2 -> 80.dp
            3 -> 80.dp
            4 -> 80.dp
            5 -> 100.dp
            6 -> 100.dp
            7 -> 150.dp
            8 -> 100.dp
            else -> 100.dp
        }
    }
    val headerCellTitle: @Composable (Int) -> Unit = { index ->
        val value = when (index) {
            0 -> stringResource(R.string.worker_name)
            1 -> stringResource(R.string.block)
            2 -> stringResource(R.string.floor)
            3 -> stringResource(R.string.unit)
            4 -> stringResource(R.string.post)
            5 -> stringResource(R.string.activity_type)
            6 -> stringResource(R.string.total_working_time)
            7 -> stringResource(R.string.supervisor_name)
            8 -> stringResource(R.string.date_submited)

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
            0 -> report.workerName
            1 -> report.blockName
            2 -> report.floorName ?: " - "
            3 -> report.unitName ?: " - "
            4 -> report.post
            5 -> report.activity
            6 -> report.totalTime
            7 -> report.supervisorName ?: ""
            8 -> report.submitted
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
                    LazyColumn{
                        items(needs.result.blocs) {
                            Surface(selected = false, onClick = {
                                viewmodel.blockId.value=it.id
                                viewmodel.blockName.value=it.name
                                viewmodel.fetchReports()
                                coroutineScope.launch {
                                    sheetStateSelect.hide()
                                }
                            }, color = Color.Transparent, modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.default_margin)) {
                                Text(text = it.name)
                            }
                            CustomDivider()
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.spacing.default_margin)
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp, MaterialTheme.customColorsPalette.cardBack,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                   coroutineScope.launch {
                                       sheetStateSelect.show()
                                   }
                                }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = MaterialTheme.spacing.default_margin),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                DrawableText(
                                    text = if (viewmodel.blockName.value.isEmpty())
                                        stringResource(id = R.string.block)
                                    else viewmodel.blockName.value,
                                    icon = painterResource(
                                        id = R.drawable.ic_blocs
                                    ),
                                    style = TextStyle(color = if (viewmodel.blockName.value.isEmpty()) MaterialTheme.colors.surface else
                                        MaterialTheme.colors.primary, fontFamily = iranSansFamily
                                    )
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }

                        }
                    }

                    VerticalDefaultMargin()

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
                                Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null,
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
                                    border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
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
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                VerticalSmallSpacer()
                                Table(
                                    columnCount = 9,
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
