package com.xumaohuai.mono.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.xumaohuai.mono.android.data.MockMonoRepository
import com.xumaohuai.mono.android.ui.MonoApp
import com.xumaohuai.mono.android.ui.theme.MonoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonoTheme {
                MonoApp(repository = MockMonoRepository())
            }
        }
    }
}
