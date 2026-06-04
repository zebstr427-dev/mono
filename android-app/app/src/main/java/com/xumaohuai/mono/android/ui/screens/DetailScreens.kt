package com.xumaohuai.mono.android.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircle
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.xumaohuai.mono.android.media.MediaControllerViewModel
import com.xumaohuai.mono.android.model.MonoItem
import com.xumaohuai.mono.android.ui.components.MonoRemoteImage
import com.xumaohuai.mono.android.ui.components.MonoTopBar
import com.xumaohuai.mono.android.ui.theme.MonoMuted
import com.xumaohuai.mono.android.ui.theme.MonoPageBg
import com.xumaohuai.mono.android.ui.theme.MonoText
import kotlinx.coroutines.delay

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ReaderScreen(item: MonoItem?, onBack: () -> Unit) {
    DetailScaffold(title = item?.title ?: "阅读", onBack = onBack) {
        if (item == null) {
            MissingItem()
            return@DetailScaffold
        }
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    settings.javaScriptEnabled = false
                    setBackgroundColor(android.graphics.Color.rgb(245, 247, 248))
                    loadDataWithBaseURL(null, item.detailHtml.ifBlank { fallbackHtml(item) }, "text/html", "UTF-8", null)
                }
            },
        )
    }
}

@Composable
fun GalleryScreen(item: MonoItem?, onBack: () -> Unit) {
    DetailScaffold(title = item?.title ?: "画册", onBack = onBack) {
        if (item == null) {
            MissingItem()
            return@DetailScaffold
        }
        val images = if (item.images.isNotEmpty()) item.images else listOf(item.thumb)
        val pagerState = rememberPagerState(pageCount = { images.size })
        Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    MonoRemoteImage(images[page], modifier = Modifier.fillMaxWidth())
                }
            }
            Text(
                text = "${pagerState.currentPage + 1} / ${images.size}",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(18.dp),
            )
        }
    }
}

@Composable
fun MusicScreen(
    item: MonoItem?,
    onBack: () -> Unit,
    mediaViewModel: MediaControllerViewModel = viewModel(),
) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }
    val state by mediaViewModel.state.collectAsStateWithLifecycle()

    DisposableEffect(player, item?.musicUrl) {
        val url = item?.musicUrl
        if (item != null && url != null) {
            mediaViewModel.load(item.songName.ifBlank { item.title }, item.artist, item.musicDurationSeconds)
            player.setMediaItem(MediaItem.fromUri(url))
            player.prepare()
        }
        onDispose { player.release() }
    }

    PlayerProgressSync(player, mediaViewModel)

    DetailScaffold(title = "音乐", onBack = onBack) {
        if (item == null) {
            MissingItem()
            return@DetailScaffold
        }
        Column(
            modifier = Modifier.fillMaxSize().background(MonoPageBg).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MonoRemoteImage(item.thumb, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(22.dp))
            Text(state.title.ifBlank { item.title }, color = MonoText, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(state.subtitle.ifBlank { item.category }, color = MonoMuted, fontSize = 14.sp, modifier = Modifier.padding(top = 6.dp))
            LinearProgressIndicator(
                progress = { if (state.durationMs > 0) state.positionMs.toFloat() / state.durationMs.toFloat() else 0f },
                modifier = Modifier.fillMaxWidth().padding(top = 28.dp),
            )
            IconButton(
                onClick = {
                    if (player.isPlaying) player.pause() else player.play()
                    mediaViewModel.setPlaying(player.isPlaying)
                },
                modifier = Modifier.size(86.dp).padding(top = 22.dp),
            ) {
                Icon(
                    imageVector = if (state.isPlaying) Icons.Outlined.PauseCircle else Icons.Outlined.PlayCircle,
                    contentDescription = "播放或暂停",
                    tint = MonoText,
                    modifier = Modifier.size(72.dp),
                )
            }
        }
    }
}

@Composable
fun VideoScreen(item: MonoItem?, onBack: () -> Unit) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(player, item?.videoUrl) {
        val url = item?.videoUrl
        if (url != null) {
            player.setMediaItem(MediaItem.fromUri(url))
            player.prepare()
        }
        onDispose { player.release() }
    }

    DetailScaffold(title = "视频", onBack = onBack) {
        if (item == null) {
            MissingItem()
            return@DetailScaffold
        }
        Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = player
                        useController = true
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().weight(1f),
            )
            Column(modifier = Modifier.background(MonoPageBg).padding(18.dp)) {
                Text(item.title, color = MonoText, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(item.description, color = MonoMuted, fontSize = 14.sp, lineHeight = 21.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
private fun DetailScaffold(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().background(MonoPageBg)) {
        MonoTopBar(title = title, onBack = onBack)
        content()
    }
}

@Composable
private fun MissingItem() {
    Column(
        modifier = Modifier.fillMaxSize().background(MonoPageBg).padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("内容不存在", color = MonoText, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text("请返回上一页重新选择。", color = MonoMuted, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun PlayerProgressSync(
    player: ExoPlayer,
    mediaViewModel: MediaControllerViewModel,
) {
    LaunchedEffect(player) {
        while (true) {
            mediaViewModel.setProgress(player.currentPosition, player.duration.takeIf { it != C.TIME_UNSET } ?: 0L)
            mediaViewModel.setPlaying(player.isPlaying)
            delay(500)
        }
    }
}

private fun fallbackHtml(item: MonoItem) = """
    <html>
    <body style="font-family:sans-serif;padding:22px;line-height:1.8;background:#f5f7f8;color:#222;">
      <h1>${item.title}</h1>
      <p>${item.description}</p>
      <p>${item.text}</p>
    </body>
    </html>
""".trimIndent()
