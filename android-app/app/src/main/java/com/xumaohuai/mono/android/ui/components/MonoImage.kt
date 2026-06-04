package com.xumaohuai.mono.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.xumaohuai.mono.android.model.MonoImage

@Composable
fun MonoRemoteImage(
    image: MonoImage,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio((image.width.toFloat() / image.height.toFloat()).coerceIn(0.65f, 1.8f))
            .background(Color(0xFFE7ECEF)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.Outlined.Image, contentDescription = null, tint = Color(0xFF9EA7AE))
        AsyncImage(
            model = image.url,
            contentDescription = image.description,
            contentScale = contentScale,
            modifier = Modifier.matchParentSize(),
        )
    }
}
