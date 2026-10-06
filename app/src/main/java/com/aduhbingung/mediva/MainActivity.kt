package com.aduhbingung.mediva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aduhbingung.mediva.ui.auth.MedivaAuthApp
import com.aduhbingung.mediva.ui.theme.MedivaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MedivaTheme {
                MedivaAuthApp()
            }
        }
    }
}