package com.example.touska.screens.unitScreen

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
fun unitScreen(
    navController: NavController,
    viewmodel: UnitViewModel = hiltViewModel(),
    floor_id: Int,
    floor_name:String,
    bloc_name:String
) {
    val units = viewmodel.units.observeAsState().value
    val addUnit = viewmodel.addUnit.observeAsState().value
    val updateUnit = viewmodel.updateUnit.observeAsState().value
    val deleteUnit = viewmodel.deleteUnit.observeAsState().value



    var isUpdate by remember {
        mutableStateOf(false)
    }

    var isAddUnit by remember {
        mutableStateOf(false)
    }

    var unit_name by remember {
        mutableStateOf(TextFieldValue(""))
    }


    var unit_id_for_update by remember {
        mutableStateOf(0)
    }
    var unit_id_for_delete by remember {
        mutableStateOf(0)
    }



    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.getUnits(floor_id)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addUnit) {
        when (addUnit) {
            is Resource.Failure -> {
                addUnit.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                unit_name = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updateUnit) {
        when (updateUnit) {
            is Resource.Failure -> {
                updateUnit.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                unit_name = TextFieldValue("")

            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deleteUnit) {
        when (deleteUnit) {
            is Resource.Failure -> {
                deleteUnit.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.unit_deleted_suucessfully).toastLong(context)
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
                        stringResource(R.string.updateUnit)
                    } else {
                        stringResource(R.string.add_new_unit)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = unit_name,
                    onValueChange = {
                        unit_name = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.unit_name))
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
                        viewmodel.updateUnit(
                            unit_name.text,
                            unit_id_for_update
                        )
                    } else {
                        viewmodel.createUnit(unit_name.text, floor_id)
                    }
                }
                ) {
                    if (addUnit is Resource.IsLoading) {
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
            CustomTopAppbar(title = "${bloc_name} > ${floor_name}",
                navController = navController)
            }
            
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when(units) {
                        is Resource.Failure -> {
                            failure(message = units.message) {
                                viewmodel.getUnits(floor_id)
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()
                        }
                        is Resource.Success ->  {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(units is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getUnits(floor_id)
                                }) {
                                if (units.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_units))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(units.result.size) { position ->
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
                                                            Text(text = units.result[position].name)
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
                                                                            unit_name =
                                                                                TextFieldValue(
                                                                                    units.result[position].name
                                                                                )
                                                                            unit_id_for_update =
                                                                                units.result[position].id
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
                                                                        unit_id_for_delete =
                                                                            units.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )



                                                        }
                                                    }

                                                    if (position != units.result.size - 1) {
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
                            unit_name = TextFieldValue("")
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_unit))
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
                viewmodel.deleteUnit(unit_id_for_delete)
            },
        )
    }




}







