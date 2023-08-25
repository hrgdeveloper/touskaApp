package com.example.touska.screens.floorScreen

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
fun floorScreen(
    navController: NavController,
    viewmodel: FloorViewModel = hiltViewModel(),
    bloc_id: Int,
    bloc_name:String
) {
    val floors = viewmodel.floors.observeAsState().value
    val addFloor = viewmodel.addFloor.observeAsState().value
    val updateFloor = viewmodel.updateFloor.observeAsState().value
    val deleteFloor = viewmodel.deleteFloor.observeAsState().value



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
        viewmodel.getFloors(bloc_id)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addFloor) {
        when (addFloor) {
            is Resource.Failure -> {
                addFloor.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                floor_name = TextFieldValue("")
                floor_number = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updateFloor) {
        when (updateFloor) {
            is Resource.Failure -> {
                updateFloor.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                floor_name = TextFieldValue("")
                floor_number = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deleteFloor) {
        when (deleteFloor) {
            is Resource.Failure -> {
                deleteFloor.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.floor_deleted_suucessfully).toastLong(context)
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
                        stringResource(R.string.updateFloor)
                    } else {
                        stringResource(R.string.add_new_floor)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = floor_name,
                    onValueChange = {
                        floor_name = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.floor_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedBorderColor = MaterialTheme.colors.secondary,
                    ),
                )
                VerticalDefaultMargin()

                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = floor_number,
                    onValueChange = {
                        floor_number = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.floor_number))
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
                        viewmodel.updateFloor(
                            floor_name.text,
                            floor_number.text.toInt(),
                            floor_id_for_update,
                            bloc_id,
                        )
                    } else {
                        viewmodel.createFloor(floor_name.text, floor_number.text.toInt(), bloc_id)
                    }
                }
                ) {
                    if (addFloor is Resource.IsLoading) {
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
            CustomTopAppbar(title = bloc_name, navController = navController)
            }
            
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when(floors) {
                        is Resource.Failure -> {
                            failure(message = floors.message) {
                                viewmodel.getFloors(bloc_id)
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()

                        }
                        is Resource.Success ->  {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(floors is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getFloors(bloc_id)
                                }) {
                                if (floors.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_floors))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(floors.result.size) { position ->
                                                Column() {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                              navController.navigate(
                                                                  MainNavigation.Unit.withArgs(
                                                                      floors.result[position].id.toString(),
                                                                      floors.result[position].name,
                                                                      bloc_name
                                                                  )
                                                              )
                                                            }
                                                            .padding(MaterialTheme.spacing.default_margin),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        Row() {
                                                            Text(text = floors.result[position].name)
                                                            Text(text = " (${floors.result[position].number})")
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
                                                                            floor_name =
                                                                                TextFieldValue(
                                                                                    floors.result[position].name
                                                                                )
                                                                            floor_number =
                                                                                TextFieldValue(
                                                                                    floors.result[position].number.toString()
                                                                                )
                                                                            floor_id_for_update =
                                                                                floors.result[position].id
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
                                                                        floor_id_for_delete =
                                                                            floors.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )

                                                            Icon(
                                                                modifier = Modifier.mirror(),
                                                                imageVector = Icons.Default.ChevronRight,
                                                                contentDescription = null
                                                            )

                                                        }
                                                    }

                                                    if (position != floors.result.size - 1) {
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
                            floor_name = TextFieldValue("")
                            floor_number = TextFieldValue("")
                            isUpdate = false
                        }
                    }) {
                        Text(text = stringResource(id = R.string.add_new_floor))
                    }
                    VerticalDefaultMargin()
                }
            }
        }

    }

    if (openDeleteDialog.value) {
        CustomAlertDialog(
            onDismiss = { openDeleteDialog.value = false },
            title = stringResource(id = R.string.delete_floor),
            text = stringResource(id = R.string.confirm_delete_floor),
            onConfirmButton = {
                viewmodel.deleteBloc(floor_id_for_delete, bloc_id)
            },
        )
    }




}







