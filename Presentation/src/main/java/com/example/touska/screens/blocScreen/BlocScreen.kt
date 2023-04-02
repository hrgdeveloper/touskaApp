package com.example.touska.screens.blocScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.domain.models.Bloc
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
fun blocScreen(
    navController: NavController,
    viewmodel: BlocViewModel = hiltViewModel()
) {
    val blocs = viewmodel.blocs.observeAsState().value
    val addBloc = viewmodel.addBloc.observeAsState().value
    val updateBloc = viewmodel.updateBloc.observeAsState().value
    val deleteBloc = viewmodel.deleteBloc.observeAsState().value

    var isUpdate by remember {
        mutableStateOf(false)
    }
    var bloc_value by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var bloc_id_for_update by remember {
        mutableStateOf(0)
    }
    var bloc_id_for_delete by remember {
        mutableStateOf(0)
    }


    val openDeleteDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewmodel.getBlocs()
    }

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }


    LaunchedEffect(key1 = addBloc) {
        when (addBloc) {
            is Resource.Failure -> {
                addBloc.returnProperMessage(context).toastLong(context)

            }
            Resource.IsLoading -> {

            }
            is Resource.Success -> {
                sheetState.hide()
                bloc_value = TextFieldValue("")
            }
            null -> {

            }
        }
    }

    LaunchedEffect(key1 = updateBloc) {
        when (updateBloc) {
            is Resource.Failure -> {
                updateBloc.returnProperMessage(context).toastLong(context)

            }
            Resource.IsLoading -> {

            }

            is Resource.Success -> {
                sheetState.hide()
                bloc_value = TextFieldValue("")
            }
            null -> {

            }
        }
    }

    LaunchedEffect(key1 = deleteBloc) {
        when (deleteBloc) {
            is Resource.Failure -> {
                deleteBloc.returnProperMessage(context).toastLong(context)

            }
            Resource.IsLoading -> {

            }

            is Resource.Success -> {
                openDeleteDialog.value=false
                context.resources.getString(R.string.bloc_deleted_suucessfully).toastLong(context)
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

                Text(
                    text = if (isUpdate) {
                        stringResource(R.string.update_bloc)
                    } else {
                        stringResource(R.string.add_new_bloc)
                    }, fontSize = 20.sp,
                    fontFamily = iranSansFamily, fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = bloc_value,
                    onValueChange = {
                        bloc_value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.default_margin),
                    label = {
                        Text(text = stringResource(R.string.bloc_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colors.surface,
                        focusedBorderColor = MaterialTheme.colors.secondary
                    )
                )
                VerticalDefaultMargin()
                ConfirmButton(onclick = {
                    if (isUpdate) {
                        viewmodel.updateBloc(bloc_value.text, bloc_id_for_update)
                    } else {
                        viewmodel.createBloc(bloc_value.text)
                    }

                }) {
                    if (addBloc is Resource.IsLoading || updateBloc is Resource.IsLoading) {
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
            .fillMaxSize()

    ) {
        Scaffold(topBar = {
            CustomTopAppbar(title = stringResource(id = R.string.blocks_manage), navController = navController)
        }) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    when (blocs) {
                        is Resource.Failure -> {
                            failure(message = blocs.message) {
                                viewmodel.getBlocs()
                            }
                        }
                        Resource.IsLoading -> {
                            CircularProgressBox()
                        }
                        is Resource.Success -> {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(blocs is Resource.IsLoading),
                                onRefresh = {
                                    viewmodel.getBlocs()
                                }) {
                                if (blocs.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_bloc))
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(blocs.result.size) { position ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(MaterialTheme.spacing.default_margin)
                                                        .clickable {
                                                            navController.navigate(
                                                                MainNavigation.Floor.withArgs(
                                                                    blocs.result[position].id.toString(),
                                                                    blocs.result[position].name
                                                                )
                                                            )
                                                        }
                                                    ,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {

                                                    Text(text = blocs.result[position].name)


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
                                                                        bloc_value =
                                                                            TextFieldValue(blocs.result[position].name)
                                                                        bloc_id_for_update =
                                                                            blocs.result[position].id
                                                                    }

                                                                }
                                                        )

                                                        Icon(
                                                            imageVector = Icons.Default.Delete,
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .padding(start = MaterialTheme.spacing.small_margin)
                                                                .clickable {
                                                                    openDeleteDialog.value = true
                                                                    bloc_id_for_delete =
                                                                        blocs.result[position].id
                                                                },
                                                            tint = Color.Red

                                                        )

                                                        Icon(
                                                            modifier = Modifier.mirror(),
                                                            imageVector = Icons.Default.ChevronRight,
                                                            contentDescription = null
                                                        )
                                                    }


                                                }
                                                if (position != blocs.result.size - 1) {
                                                    customDivider()
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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConfirmButton(onclick = {
                        coroutineScope.launch {
                            if (sheetState.isVisible) sheetState.hide()
                            else sheetState.show()
                            bloc_value = TextFieldValue("")
                            isUpdate = false
                        }
                    }, content = {
                        Text(text = stringResource(id = R.string.add_new_bloc))
                    })

                    VerticalDefaultMargin()

                }

            }
        }



        if (openDeleteDialog.value) {
            CustomAlertDialog(
                onDismiss = { openDeleteDialog.value=false},
                title = stringResource(id = R.string.delete_bloc),
                text = stringResource(id = R.string.confirm_delete_bloc),
                onConfirmButton = {
                   viewmodel.deleteBloc(bloc_id_for_delete)
                }
            )

        }


    }


}