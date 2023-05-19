package com.example.touska.screens.usermanageScreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState


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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.components.*
import com.example.touska.navigation.MainNavigation
import com.example.touska.screens.usermanageInsideScreen.userManageInsideScreen
import com.example.touska.ui.theme.customColorsPalette
import com.example.touska.ui.theme.spacing
import com.example.touska.utils.UserTypes

import com.example.touska.utils.returnProperMessage
import com.example.touska.utils.toastLong
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun userManageScreen(
    navController: NavController,
    viewmodel: UserManageViewModel = hiltViewModel(),

) {
    val users = viewmodel.allUsers.observeAsState().value



    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getAllUsers()
    }



    Scaffold(
        topBar = {
            CustomTopAppbar(
                title = stringResource(R.string.manage_users),
                navController = navController,
                elevation = 0.dp
            )
        }

    ) {
                    when(users) {
                        is Resource.Failure -> {
                            failure(message = users.message) {
                                viewmodel.getAllUsers()
                            }
                        }
                        Resource.IsLoading ->  {
                            CircularProgressBox()
                        }
                        is Resource.Success ->  {
                                if (users.result.isEmpty()) {
                                    empty(message = stringResource(R.string.no_user_found))
                                } else {
                                    TabLayout(navController)
                                }

                        }
                        null -> {

                        }
                    }

    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabLayout(navController: NavController) {
    val pagerState: PagerState = rememberPagerState()

    var key by remember {
        mutableStateOf(true)
    }



    Column(
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Column() {
                Tabs(pagerState)
                TabsContent(pagerState,navController)
            }

        }
        Column(modifier = Modifier.padding(vertical = MaterialTheme.spacing.default_margin,
        horizontal = MaterialTheme.spacing.small_margin)) {
            ConfirmButton(onclick = {
              navController.navigate(MainNavigation.Register.route+"?role_id=${pagerState.currentPage+1}")
            },{
                Text(text = stringResource(R.string.add_new_user))
            })

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        stringResource(R.string.managers),
        stringResource(R.string.owners),
        stringResource(R.string.observers),
        stringResource(R.string.workers),
        stringResource(R.string.contractors)
    )

    val coroutineScope = rememberCoroutineScope()


    TabRow(

        selectedTabIndex = pagerState.currentPage,

        backgroundColor = MaterialTheme.customColorsPalette.top_bar,

        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.customTabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 5.dp,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    ) {
        // on below line we are specifying icon
        // and text for the individual tab item
        list.forEachIndexed { index, _ ->
            // on below line we are creating a tab.
            Tab(
                text = {
                    Text(
                        list[index],
                        // on below line we are specifying the text color
                        // for the text in that tab
                        color = if (pagerState.currentPage == index)
                            MaterialTheme.colors.primary
                        else
                            MaterialTheme.colors.surface,
                        fontWeight = if (pagerState.currentPage == index)
                            FontWeight.Bold else FontWeight.Normal

                    )
                },
                // on below line we are specifying
                // the tab which is selected.
                selected = pagerState.currentPage == index,
                // on below line we are specifying the
                // on click for the tab which is selected.
                onClick = {
                    // on below line we are specifying the scope.
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}

// on below line we are creating a tab content method
// in which we will be displaying the individual page of our tab .
@OptIn(ExperimentalFoundationApi::class)

@Composable
fun TabsContent(pagerState: PagerState,navController : NavController) {
    HorizontalPager(pageCount = 5, state = pagerState) { page ->
        // Our page content
        when (page) {
            // on below line we are calling tab content screen
            // and specifying data as Home Screen.
            0 -> userManageInsideScreen(navController = navController, role_id = UserTypes.Manager.role_id)
            // on below line we are calling tab content screen
            // and specifying data as Shopping Screen.
            1 -> userManageInsideScreen(navController = navController, role_id = UserTypes.Owner.role_id)
            // on below line we are calling tab content screen
            // and specifying data as Settings Screen.
            2 -> userManageInsideScreen(navController = navController,role_id = UserTypes.Observer.role_id)

            3 -> userManageInsideScreen(navController = navController, role_id = UserTypes.Worker.role_id)

            4 -> userManageInsideScreen(navController = navController, role_id = UserTypes.Contractor.role_id)

        }
    }


}




fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorWidth = 60.dp
    val currentTabWidth = currentTabPosition.width
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left + currentTabWidth / 2 - indicatorWidth / 2,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(indicatorWidth)
        .clip(shape = RoundedCornerShape(4.dp))
}








