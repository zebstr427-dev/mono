package com.xumaohuai.mono.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xumaohuai.mono.android.data.MonoRepository
import com.xumaohuai.mono.android.model.DiscoverBanner
import com.xumaohuai.mono.android.ui.components.MonoRemoteImage
import com.xumaohuai.mono.android.ui.components.MonoTopBar
import com.xumaohuai.mono.android.ui.theme.MonoMuted
import com.xumaohuai.mono.android.ui.theme.MonoPageBg

@Composable
fun DiscoverScreen(repository: MonoRepository) {
    val discover = repository.discover()
    Column(modifier = Modifier.fillMaxSize().background(MonoPageBg)) {
        MonoTopBar(title = "发现")
        LazyRow(
            contentPadding = PaddingValues(16.dp),
        ) {
            items(discover.banners, key = { it.id }) { banner ->
                BannerCard(banner)
            }
        }
        Text(
            text = "发现页列表区域在原 iOS 项目中尚未实现，这里保留空态。",
            color = MonoMuted,
            modifier = Modifier.padding(22.dp),
            lineHeight = 22.sp,
        )
    }
}

@Composable
private fun BannerCard(banner: DiscoverBanner) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .padding(end = 14.dp),
    ) {
        MonoRemoteImage(
            image = com.xumaohuai.mono.android.model.MonoImage(banner.imageUrl),
            modifier = Modifier.matchParentSize(),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color(0x99000000))
                .padding(16.dp),
        ) {
            Text(banner.title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(banner.subtitle, color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
        }
    }
}
