package com.example.touska.screens.settingScreen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.data.local.sharedpref.PrefManager
import com.example.shared.Resource
import com.example.touska.MainActivity
import com.example.touska.MainViewModel
import com.example.touska.MyApp
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.iranSansFamily
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.ThemeTypes
import com.example.touska.utils.mirror
import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun settingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    var type by remember {
        mutableStateOf(0) // 0 for language / 1 for theme / 2 for logout
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
                if (type == 0) {
                    var isPersian = settingViewModel.prefManager.getValue(
                        PrefManager.Language,
                        String::class,
                        "fa"
                    ).equals("fa")
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                settingViewModel.prefManager.setValue(
                                    PrefManager.Language,
                                    "fa"
                                )
                                MyApp.instance.language = "fa"
                                (context as MainActivity).recreate()
                            }
                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .border(
                            1.dp,
                            if (isPersian) MaterialTheme.colors.primaryVariant else Color.Transparent,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .height(56.dp), contentAlignment = Alignment.CenterStart)

                    {
                        DrawableTextImage(
                            text = stringResource(R.string.persian),
                            painterResource(id = R.drawable.ic_iran)
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                settingViewModel.prefManager.setValue(
                                    PrefManager.Language,
                                    "en"
                                )
                                MyApp.instance.language = "en"
                                (context as MainActivity).recreate()

                            }
                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .border(
                            1.dp,
                            if (!isPersian) MaterialTheme.colors.primaryVariant else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .height(56.dp), contentAlignment = Alignment.CenterStart) {
                        DrawableTextImage(
                            text = stringResource(R.string.english),
                            painterResource(id = R.drawable.ic_england)
                        )
                    }

                } else if (type == 1) {
                    val theme = settingViewModel.prefManager.getValue(
                        PrefManager.THEME,
                        String::class,
                        ThemeTypes.SYSTEM
                    )
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                settingViewModel.prefManager.setValue(
                                    PrefManager.THEME,
                                    ThemeTypes.LIGHT
                                )
                                mainViewModel.setTheme(ThemeTypes.LIGHT)
                            }
                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .border(
                            1.dp,
                            if (theme.equals(ThemeTypes.LIGHT)) MaterialTheme.colors.primaryVariant else Color.Transparent,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .height(56.dp), contentAlignment = Alignment.CenterStart)

                    {
                        DrawableText(text = stringResource(R.string.light), Icons.Default.WbSunny)
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                settingViewModel.prefManager.setValue(
                                    PrefManager.THEME,
                                    ThemeTypes.DARK
                                )
                                mainViewModel.setTheme(ThemeTypes.DARK)
                            }
                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .border(
                            1.dp,
                            if (theme.equals(ThemeTypes.DARK)) MaterialTheme.colors.primaryVariant else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .height(56.dp), contentAlignment = Alignment.CenterStart) {
                        DrawableText(
                            text = stringResource(R.string.dark),
                            painterResource(id = R.drawable.ic_moon)
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                settingViewModel.prefManager.setValue(
                                    PrefManager.THEME,
                                    ThemeTypes.SYSTEM
                                )
                                mainViewModel.setTheme(ThemeTypes.SYSTEM)
                            }
                        }
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .border(
                            1.dp,
                            if (theme.equals(ThemeTypes.SYSTEM)) MaterialTheme.colors.primaryVariant else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = MaterialTheme.spacing.default_margin)
                        .height(56.dp), contentAlignment = Alignment.CenterStart) {
                        DrawableText(
                            text = stringResource(R.string.based_on_system),
                            Icons.Default.SettingsSystemDaydream
                        )
                    }

                } else if (type == 2) {
                    Text(
                        text = stringResource(R.string.accept_logout),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.default_margin)

                    )
                    Row(modifier = Modifier.align(Alignment.End).padding(end = MaterialTheme.spacing.default_margin)) {
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent,
                            ),
                            onClick = {
                               mainViewModel.logOut()

                            }) {
                            Text(stringResource(R.string.yes))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent
                            ),
                            onClick = {
                                 coroutineScope.launch {
                                     sheetState.hide()
                                 }
                            }) {
                            Text(stringResource(R.string.cancel))
                        }

                    }

                }
                VerticalSmallSpacer()

            }

        },
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomTopAppbar(
                title = stringResource(R.string.settings),
                navController = navController,
                showBack = false
            )
            VerticalDefaultMargin()
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        type = 0
                        sheetState.show()
                    }
                }
                .padding(horizontal = MaterialTheme.spacing.default_margin)
                .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableText(
                    text = stringResource(R.string.language),
                    icon = Icons.Default.Language
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.mirror()
                )

            }
            CustomDivider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        type = 1
                        sheetState.show()
                    }
                }
                .padding(horizontal = MaterialTheme.spacing.default_margin)
                .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableText(
                    text = stringResource(R.string.theme),
                    painterResource(id = R.drawable.ic_moon)
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.mirror()
                )

            }

            CustomDivider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    coroutineScope.launch {
                        type = 2
                        sheetState.show()
                    }
                }
                .padding(horizontal = MaterialTheme.spacing.default_margin)
                .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                DrawableText(text = stringResource(R.string.logout), icon = Icons.Default.Logout)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.mirror()
                )

            }


        }

    }

}