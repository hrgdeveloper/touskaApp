package com.example.touska.screens.login
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.shared.Resource

import com.example.touska.R
import com.example.touska.components.VerticalDefaultMargin
import com.example.touska.navigation.Navigation
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong

@Composable
fun loginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val loginState = loginViewModel.login.observeAsState().value
    val context = LocalContext.current


    LaunchedEffect(loginState)   {
        when(loginState) {
            is Resource.Failure -> {

                loginState.returnProperMessage(context).toastLong(context)
            }

            is Resource.Success -> {
               navController.navigate(Navigation.Main.route) {
                   popUpTo(0)
               }
            }
            else ->{

            }
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colors.background)
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.art),
                contentDescription = "login",
                Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillBounds
            )
            Card(
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
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
                    Text(
                        text = stringResource(id = R.string.login),
                        fontFamily = iranSansFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.primary
                    )

                    VerticalDefaultMargin()

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
                                Modifier.size(24.dp)
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
                                Modifier.size(24.dp),
                                content = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                }
                            )

                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = MaterialTheme.colors.surface,
                            focusedBorderColor = MaterialTheme.colors.secondary
                        ),

                        )

                    VerticalDefaultMargin()

                    Button(
                        onClick = {
                         loginViewModel.login(email.text,password.text)
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
                    ) {
                        if (loginState is Resource.IsLoading) {
                            CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
                        }else {
                            Text(text = stringResource(R.string.enter))
                        }

                    }
                    VerticalDefaultMargin()
                    Text(
                        text = stringResource(R.string.forget_password),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondary,
                        fontFamily = iranSansFamily,
                    )

                }
            }
        }


    }

}