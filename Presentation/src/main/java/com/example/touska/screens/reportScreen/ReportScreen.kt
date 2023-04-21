package com.example.touska.screens.reportScreen

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun reportScreen(
    navController: NavController,
    reportViewModel: ReportViewModel = hiltViewModel()
) {
//    val floors = viewmodel.floors.observeAsState().value
//    val addFloor = viewmodel.addFloor.observeAsState().value
//    val updateFloor = viewmodel.updateFloor.observeAsState().value
//    val deleteFloor = viewmodel.deleteFloor.observeAsState().value


    var isUpdate by remember {
        mutableStateOf(false)
    }

    var isAddFloor by remember {
        mutableStateOf(false)
    }

    var floor_name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var floor_number by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var floor_id_for_update by remember {
        mutableStateOf(0)
    }
    var floor_id_for_delete by remember {
        mutableStateOf(0)
    }


    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        //viewmodel.getFloors(bloc_id)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

//    LaunchedEffect(key1 = addFloor) {
//        when (addFloor) {
//            is Resource.Failure -> {
//                addFloor.returnProperMessage(context).toastLong(context)
//            }
//            Resource.IsLoading -> {
//            }
//            is Resource.Success -> {
//                sheetState.hide()
//                floor_name = TextFieldValue("")
//                floor_number = TextFieldValue("")
//            }
//            null -> {
//            }
//        }
//    }

//    LaunchedEffect(key1 = updateFloor) {
//        when (updateFloor) {
//            is Resource.Failure -> {
//                updateFloor.returnProperMessage(context).toastLong(context)
//            }
//            Resource.IsLoading -> {
//            }
//
//            is Resource.Success -> {
//                sheetState.hide()
//                floor_name = TextFieldValue("")
//                floor_number = TextFieldValue("")
//            }
//            null -> {
//            }
//        }
//    }

//    LaunchedEffect(key1 = deleteFloor) {
//        when (deleteFloor) {
//            is Resource.Failure -> {
//                deleteFloor.returnProperMessage(context).toastLong(context)
//            }
//            Resource.IsLoading -> {
//            }
//            is Resource.Success -> {
//                openDeleteDialog.value = false
//                context.resources.getString(R.string.floor_deleted_suucessfully).toastLong(context)
//            }
//            null -> {
//            }
//        }
//    }
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


                VerticalDefaultMargin()


            }
        },
        modifier = Modifier
            .fillMaxSize(),

        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    VerticalDefaultMargin()
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.default_margin)) {
                         Button(onClick = {
                                navController.navigate(MainNavigation.WorkerList.route)

                         }, modifier = Modifier.weight(1f),
                           colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
                             ) {
                             Text(text = stringResource(id = R.string.add_new_report), color = Color.White)
                         }

                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {

                        },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)) {
                            DrawableText(text = stringResource(R.string.apply_filters), icon = Icons.Default.FilterAlt, tint = Color.White)

                        }

                    }



                }

                Box(
                    modifier = Modifier
                        .weight(1f),
                    ) {

                }


            }


    }
}