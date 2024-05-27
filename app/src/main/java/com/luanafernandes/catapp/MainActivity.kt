package com.luanafernandes.catapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.luanafernandes.catapp.presentation.screens.MainScreen
import com.luanafernandes.catapp.ui.theme.CatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CatAppTheme {
                MainScreen()
            }
        }
    }
}

