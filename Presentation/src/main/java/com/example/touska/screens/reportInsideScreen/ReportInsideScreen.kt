package com.example.touska.screens.reportInsideScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.BackHandler

import androidx.compose.foundation.*
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
import com.example.domain.models.Report
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

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun reportInsideScreen(
    navController: NavController,
    reportString: String,
) {
    var report  = Gson().fromJson(reportString,Report::class.java)


    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }



    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetBackgroundColor = MaterialTheme.customColorsPalette.cardBack,
        sheetContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
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
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(report.reportTimes){
                        Text(text = it.endTime + " - "+ it.startTime,
                            modifier = Modifier
                                .padding(all = MaterialTheme.spacing.default_margin)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Left
                            )
                        CustomDivider()
                    }
                }


            }

        },
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (report.pic != null) {
                    Image(
                        painter =
                        rememberImagePainter(BuildConfig.BASE_IMAGE+report.pic),
                        contentDescription = null,
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
                        Image(imageVector = Icons.Default.BrowseGallery, contentDescription =null, modifier = Modifier.size(80.dp))
                    }
                }



                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(transBack)

                ) {
                    Row(modifier = Modifier.align(Alignment.CenterStart), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier
                                .mirror()
                                .padding(MaterialTheme.spacing.default_margin)
                                .clickable {
                                    navController.navigateUp()
                                },

                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                        Text(
                            text = stringResource(R.string.report_info),
                            fontSize = 20.sp,
                            fontFamily = iranSansFamily,
                            fontWeight = FontWeight.Bold,

                            )

                    }


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
                                text = report.workerName,
                                fontSize = 24.sp,
                                fontFamily = iranSansFamily,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )



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
                                text = stringResource(R.string.date_submited),
                                icon = Icons.Default.DateRange
                            )

                            Text(
                                text = report.submitted,

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
                                text = stringResource(R.string.project_control),
                                icon = Icons.Default.Person3
                            )

                            Text(
                                text = report.supervisorName!!,

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
                                text = stringResource(R.string.block),
                                icon = Icons.Default.Desk
                            )

                            Text(
                                text = report.blockName,

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
                                text = stringResource(R.string.floor),
                                icon = Icons.Default.Web
                            )

                            Text(
                                text = report.floorName?:"-",

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
                                text = stringResource(R.string.unit),
                                icon = Icons.Default.DoorFront
                            )

                            Text(
                                text = report.unitName?:"-",

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
                                text = stringResource(R.string.post),
                                icon = Icons.Default.Workspaces
                            )

                            Text(
                                text = report.post?:"-",

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
                                text = stringResource(R.string.activity_type),
                                icon = painterResource(id = R.drawable.ic_worker)
                            )

                            Text(
                                text = report.post?:"-",

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
                                    coroutineScope.launch {
                                        sheetState.show()
                                    }

                                }
                        ) {
                            DrawableText(
                                text = stringResource(R.string.total_working_time),
                                icon = painterResource(id = R.drawable.ic_clock)
                            )

                            Row() {
                                Text(
                                    text = report.totalTime,

                                    )
                                Icon(imageVector = Icons.Default.ChevronRight , contentDescription =null, modifier = Modifier.mirror())
                            }

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
                                text = stringResource(R.string.contractor),
                                icon = Icons.Default.Person3
                            )

                            Row() {
                                Text(
                                    text = report.contractorName?: stringResource(id = R.string.free_worker),
                                    )

                            }

                        }
                        CustomDivider()

                        VerticalDefaultMargin()
                        Text(text = stringResource(R.string.describtions))
                        VerticalSmallSpacer()
                        Text(text = report.description?:"")




                    }


                }
            }
        }

    }










}











