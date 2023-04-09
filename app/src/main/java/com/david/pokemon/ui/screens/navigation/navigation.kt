package com.david.pokemon.ui.screens.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.david.pokemon.ui.screens.search.SearchViewModel
import com.david.pokemon.ui.theme.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation(navController: NavHostController,  searchViewModel: SearchViewModel = hiltViewModel()) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible =
                !isNeedToHideTabs(navController.currentBackStackEntryAsState())
            ) {
                BottomNavigation(navController = navController)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(DeepBlue)
                .fillMaxSize()
        ) {
//            val searchTermState = rememberSaveable { mutableStateOf("") }

            NavigationGraph(navController = navController)

            if (isNeedToShowSearchBar(navController.currentBackStackEntryAsState())) {
                SearchBar(
                    searchViewModel = searchViewModel ,
                    onSearchClicked = { isClick ->
                        if (isClick) {
                            navController.navigate(Screen.Search.route)
                        } else {
                            navController.popBackStack()
                        }
                    },
                    onSearchTermChanged = { searchTerm ->
//                        Log.i("dddddyyyy","1-> "+searchTerm)
//                        searchViewModel.searchPoke(searchTerm)

                    }
                )
            }
        }
    }
}


@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Screen.Main,
        Screen.Favorites,
        Screen.Setting,
    )
    BottomNavigation(
        backgroundColor = BottomNavigationColor,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        fun isSelectedTab(screen: Screen) = (currentRoute == screen.route)
        fun getSelectedColor(screen: Screen) =
            if (isSelectedTab(screen)) screen.iconColor else Color.Gray

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = getSelectedColor(item)

                    )
                },
                label = {
                    Text(
                        color = getSelectedColor(item),
                        text = item.title,
                        fontSize = if (isSelectedTab(item)) 17.sp else 13.sp,
                    )
                },
                selectedContentColor = Color.White,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


public fun isNeedToHideTabs(stateNavBackStack: State<NavBackStackEntry?>): Boolean {
    val currentRoute = stateNavBackStack.value?.destination?.route ?: return true
    return Screen.isNeedToHideTabs(currentRoute)
}

public fun isNeedToShowSearchBar(stateNavBackStack: State<NavBackStackEntry?>): Boolean {
    val currentRoute = stateNavBackStack.value?.destination?.route ?: return true
    return Screen.isNeedToShowSearchBar(currentRoute)
}


@Composable
fun SearchBar(
    onSearchClicked: (Boolean) -> Unit,
    onSearchTermChanged: (String) -> Unit,
    searchViewModel: SearchViewModel,

    ) {
    BoxWithConstraints {
        val maxWidth = constraints.maxWidth
        val (isExpanded, setIsExpanded) = remember { mutableStateOf(false) }
        val (searchText, setSearchText) = remember { mutableStateOf("") }
        val width: Dp by animateDpAsState(if (isExpanded) maxWidth.dp else 60.dp)

        BackHandler(enabled = isExpanded, onBack = {
            if (isExpanded) {
                onSearchClicked(false)
                setIsExpanded(false)
            }
        })

        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(width = width, height = 60.dp),
                shape = CircleShape,
                color = SearchBarColor,
                elevation = 4.dp,
            ) {
                if (!isExpanded) {
                    IconButton(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = {
                            setIsExpanded(!isExpanded)
                            onSearchClicked(true)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = com.david.pokemon.R.drawable.ic_search),
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(SearchBarColor)
                    ) {
                        TextField(
                            value = searchText,
                            onValueChange = { text->
                                Log.i("dddyyy", "text changed: $text")
                                searchViewModel.searchPoke(text.trim())
//                                onSearchTermChanged(text)
                                setSearchText(text)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .align(Alignment.CenterStart),
                            placeholder = { Text("Search") },
                            textStyle = MaterialTheme.typography.body1,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = SearchBarColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                textColor = Color.White,
                                placeholderColor = Color.LightGray,
                                cursorColor = Color.LightGray
                            ),
                            singleLine = true
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                setIsExpanded(!isExpanded)
                                setSearchText("")
                                onSearchClicked(false)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = com.david.pokemon.R.drawable.ic_close),
                                contentDescription = "Close search",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
