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
        installSplashScreen()

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
}
