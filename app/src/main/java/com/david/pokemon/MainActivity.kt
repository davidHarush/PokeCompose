package com.david.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.david.pokemon.ui.screens.navigation.Navigation
import com.david.pokemon.ui.theme.DeepBlue
import com.david.pokemon.ui.theme.PokemonTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
//        splashScreen.setKeepOnScreenCondition {
//            for (i in 0..8000) {
//
//            }
//            false
//        }

        setContent {
            PokemonTheme {

                val systemUiController = rememberSystemUiController()
                val navController = rememberNavController()
                systemUiController.apply {
                    setStatusBarColor(color = DeepBlue, darkIcons = false)
                    setNavigationBarColor(color = DeepBlue, darkIcons = false)
                }

                Navigation(navController)
            }
        }
    }


//@Composable
//fun Navigation(navController: NavHostController) {
//    NavHost(navController = navController, startDestination = ScreenDestination.Main.route) {
//
//        composable(route = ScreenDestination.Main.route) {
//            MainScreen(navController = navController)
//        }
//
//        composable(
//            route = ScreenDestination.Detail.route + "/{name}/{id}", arguments = listOf(
//                navArgument("name") {
//                    type = NavType.StringType
//                    nullable = false
//                },
//                navArgument("id") {
//                    type = NavType.StringType
//                    nullable = false
//                })
//        )
//        { navBackStackEntry ->
//            val name: String = navBackStackEntry.arguments?.getString("name") ?: ""
//            val id: String = navBackStackEntry.arguments?.getString("id") ?: ""
//            DetailScreen(
//                navController = navController,
//                pokeCoreData = PokeCoreDataCharacter(
//                    id = id.toInt(), name = name
//                )
//            )
//        }
//    }


}
