package com.example.touska.screens.contractScreen

import android.annotation.SuppressLint
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
fun contractScreen(
    navController: NavController,
    viewmodel: ContractViewModel = hiltViewModel(),

    ) {
    val contracts = viewmodel.contracts.observeAsState().value
    val addContract = viewmodel.addContract.observeAsState().value
    val updateContract = viewmodel.updateContract.observeAsState().value
    val deleteContract = viewmodel.deleteContract.observeAsState().value



    var isUpdate by remember {
        mutableStateOf(false)
    }

    var isAdd by remember {
        mutableStateOf(false)
    }


    var contractTitle by remember {
        mutableStateOf(TextFieldValue(""))
    }


    var contractIdForUpdate by remember {
        mutableStateOf(0)
    }
    var contractIdForDelete by remember {
        mutableStateOf(0)
    }



    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.getContracts()
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    LaunchedEffect(key1 = addContract) {
        when (addContract) {
            is Resource.Failure -> {
                addContract.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                sheetState.hide()
                contractTitle = TextFieldValue("")
            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = updateContract) {
        when (updateContract) {
            is Resource.Failure -> {
                updateContract.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }

            is Resource.Success -> {
                sheetState.hide()
                contractTitle = TextFieldValue("")

            }
            null -> {
            }
        }
    }

    LaunchedEffect(key1 = deleteContract) {
        when (deleteContract) {
            is Resource.Failure -> {
                deleteContract.returnProperMessage(context).toastLong(context)
            }
            Resource.IsLoading -> {
            }
            is Resource.Success -> {
                openDeleteDialog.value = false
                context.resources.getString(R.string.contract_deleted_suucessfully).toastLong(context)
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
                        stringResource(R.string.updateContract)
                    } else {
                        stringResource(R.string.add_new_Contract)
                    },
                    fontSize = 20.sp,
                    fontFamily = iranSansFamily,
                    fontWeight = FontWeight.Bold,
                )
                OutlinedTextField(
                    value = contractTitle,
                    onValueChange = {
                        contractTitle = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.contract_name))
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
                        viewmodel.updateContract(
                            contractTitle.text,
                            contractIdForUpdate
                        )
                    } else {
                        viewmodel.createContract(contractTitle.text)
                    }
                }
                ) {
                    if (addContract is Resource.IsLoading) {
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
            CustomTopAppbar(title = stringResource(R.string.contract_manage),
                navController = navController)
            }
            
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f),

                    ) {

                    when(contracts) {
                        is Resource.Failure -> {
                            failure(message = contracts.message) {
                                viewmodel.getContracts()
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()
                        }
                        is Resource.Success ->  {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(contracts is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getContracts()
                                }) {
                                if (contracts.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_contract))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                        ) {
                                            items(contracts.result.size) { position ->
                                                Column() {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(MaterialTheme.spacing.default_margin),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                    ) {
                                                        Row() {
                                                            Text(text = contracts.result[position].title)
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
                                                                            contractTitle =
                                                                                TextFieldValue(
                                                                                    contracts.result[position].title
                                                                                )
                                                                            contractIdForUpdate =
                                                                                contracts.result[position].id
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
                                                                        contractIdForDelete =
                                                                            contracts.result[position].id
                                                                    },
                                                                tint = Color.Red,

                                                                )

                                                        }
                                                    }

                                                    if (position != contracts.result.size - 1) {
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
                            contractTitle = TextFieldValue("")
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_Contract))
                    })
                    VerticalDefaultMargin()
                }
            }
        }
        
   
    }

    if (openDeleteDialog.value) {
        CustomAlertDialog(
            onDismiss = { openDeleteDialog.value = false },
            title = stringResource(id = R.string.delete_contract),
            text = stringResource(id = R.string.confirm_delete_contract),
            onConfirmButton = {
                viewmodel.deleteContract(contractIdForDelete)
            },
        )
    }




}







