package com.xumaohuai.mono.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xumaohuai.mono.android.ui.components.MonoTopBar
import com.xumaohuai.mono.android.ui.theme.MonoMuted
import com.xumaohuai.mono.android.ui.theme.MonoPageBg
import com.xumaohuai.mono.android.ui.theme.MonoText

@Composable
fun PlaceholderTab(title: String, message: String) {
    Column(modifier = Modifier.fillMaxSize().background(MonoPageBg)) {
        MonoTopBar(title = title)
        Column(
            modifier = Modifier.fillMaxSize().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(title, color = MonoText, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(message, color = MonoMuted, fontSize = 14.sp, lineHeight = 22.sp, modifier = Modifier.padding(top = 12.dp))
        }
    }
}
