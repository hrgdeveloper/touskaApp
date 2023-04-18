package com.example.touska.screens.mainScreen.registerScreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.data.BuildConfig
import com.example.domain.models.UserManage
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.navigation.Navigation
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.UserTypes
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import com.google.gson.Gson


import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun registerScreen(
    navController: NavController,
    viewmodel: RegisterViewModel = hiltViewModel(),
    role_id: Int

) {
    val needs = viewmodel.needs.observeAsState().value
    val register = viewmodel.register.observeAsState().value



    var name by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var mobile by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    var expandedContract by remember { mutableStateOf(false) }
    var selectedContractText by remember { mutableStateOf("") }

    var contractId by remember {
        mutableStateOf(0)
    }


    var expandedPost by remember { mutableStateOf(false) }
    var selectedPostText by remember { mutableStateOf("") }

    var postId by remember {
        mutableStateOf(0)
    }


    val contextt = LocalContext.current


    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val selectedImageBitmap = remember { mutableStateOf<Bitmap?>(null) }

    val pickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri.value = uri
            selectedImageBitmap.value = uri?.let { getBitmapFromUri(contextt, it) }
        }

    LaunchedEffect(key1 = register) {
        when (register) {
            is Resource.Failure -> {
                register.returnProperMessage(contextt).toastLong(contextt)
            }

            is Resource.Success -> {
                Toast.makeText(contextt,  contextt.getString(R.string.registered_successfully) , Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
    }







    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getRegisterNeeds()
    }


    val coroutineScope = rememberCoroutineScope()


    when (needs) {
        is Resource.Failure -> {
            failure(message = needs.message) {
                viewmodel.getRegisterNeeds()
            }
        }
        Resource.IsLoading -> {
            CircularProgressBox()
        }
        is Resource.Success -> {
            Scaffold(topBar = {
                CustomTopAppbar(
                    title = stringResource(id = R.string.add_new_user),
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

                        selectedImageBitmap.value?.let { myBitmap ->
                            Image(
                                bitmap = myBitmap.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        pickImage.launch("image/*")
                                    }
                            )
                        } ?: kotlin.run {
                            Image(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        pickImage.launch("image/*")
                                    }
                            )
                        }

                        //profile picture
                        VerticalDefaultMargin()
                        //name text field
                        OutlinedTextField(
                            value = name,
                            onValueChange = {
                                name = it
                            },

                            Modifier
                                .fillMaxWidth(),
                            label = {
                                Text(text = stringResource(R.string.name_and_family))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Person, contentDescription = null,
                                    Modifier.size(16.dp),
                                    tint = MaterialTheme.colors.surface
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MaterialTheme.colors.surface,
                                focusedBorderColor = MaterialTheme.colors.secondary
                            ),
                        )

                        VerticalDefaultMargin()
                        //email text field
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },

                            Modifier
                                .fillMaxWidth(),
                            label = {
                                Text(text = stringResource(R.string.email))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email, contentDescription = null,
                                    Modifier.size(16.dp),
                                    tint = MaterialTheme.colors.surface
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MaterialTheme.colors.surface,
                                focusedBorderColor = MaterialTheme.colors.secondary
                            ),
                        )
                        VerticalDefaultMargin()

                        //password text field
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },

                            Modifier
                                .fillMaxWidth(),
                            label = {
                                Text(text = stringResource(R.string.password))
                            },
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            leadingIcon = {
                                val icon =
                                    if (passwordVisibility) ImageVector.vectorResource(id = R.drawable.ic_hide_eye) else ImageVector.vectorResource(
                                        id = R.drawable.ic_visible_eye
                                    )
                                IconButton(
                                    {
                                        passwordVisibility = !passwordVisibility
                                    },
                                    Modifier.size(16.dp),
                                    content = {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = Color.Gray
                                        )
                                    }
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MaterialTheme.colors.surface,
                                focusedBorderColor = MaterialTheme.colors.secondary
                            ),
                        )

                        VerticalDefaultMargin()

                        //mobile text field
                        OutlinedTextField(
                            value = mobile,
                            onValueChange = {
                                mobile = it
                            },

                            Modifier
                                .fillMaxWidth(),
                            label = {
                                Text(text = stringResource(R.string.mobile))
                            },

                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Phone, contentDescription = null,
                                    Modifier.size(16.dp),
                                    tint = MaterialTheme.colors.surface
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = MaterialTheme.colors.surface,
                                focusedBorderColor = MaterialTheme.colors.secondary
                            ),
                        )
                        if (role_id == 3 || role_id == 4) {
                            VerticalDefaultMargin()
                            //contracts types
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = expandedContract,
                                onExpandedChange = {
                                    expandedContract = !expandedContract
                                }
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    readOnly = true,
                                    value = selectedContractText,
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.contract_type)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedContract
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
                                    expanded = expandedContract,
                                    onDismissRequest = {
                                        expandedContract = false
                                    }
                                ) {
                                    needs.result.contracts.forEach { selectedContract ->
                                        DropdownMenuItem(

                                            modifier = Modifier.fillMaxWidth(),

                                            onClick = {
                                                selectedContractText = selectedContract.title
                                                contractId = selectedContract.id
                                                expandedContract = false
                                            }
                                        ) {
                                            Text(text = selectedContract.title)
                                        }
                                    }
                                }
                            }
                        }

                        if (role_id == 4) {
                            VerticalDefaultMargin()
                            ExposedDropdownMenuBox(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = expandedPost,
                                onExpandedChange = {
                                    expandedPost = !expandedPost
                                }
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    readOnly = true,
                                    value = selectedPostText,
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.post_type)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedPost
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
                                    expanded = expandedPost,
                                    onDismissRequest = {
                                        expandedPost = false
                                    }
                                ) {
                                    needs.result.posts.forEach { selectedPost ->
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxWidth(),
                                            onClick = {
                                                selectedPostText = selectedPost.title
                                                postId = selectedPost.id
                                                expandedPost = false
                                            }
                                        ) {
                                            Text(text = selectedPost.title)
                                        }
                                    }
                                }
                            }


                        }


                    }
                    Box(
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.spacing.small_margin,
                            vertical = MaterialTheme.spacing.default_margin
                        )
                    ) {
                        ConfirmButton(onclick = {
                            viewmodel.register(
                                name.text,
                                password.text,
                                email.text,
                                mobile.text,
                                role_id,
                                selectedImageUri.value,
                                if (role_id == 3 || role_id == 4) contractId else null,
                                if (role_id == 4) postId else null,
                                1,
                                contextt
                            )
                        }) {
                            if (register is Resource.IsLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(text = "ثبت کاربر")
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

public fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}










