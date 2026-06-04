package com.xumaohuai.mono.android.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xumaohuai.mono.android.data.MonoRepository
import com.xumaohuai.mono.android.model.RecommendType
import com.xumaohuai.mono.android.ui.components.MonoTopBar
import com.xumaohuai.mono.android.ui.components.RecommendCard
import com.xumaohuai.mono.android.ui.theme.MonoNavDark
import com.xumaohuai.mono.android.ui.theme.MonoPageBg
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendScreen(
    repository: MonoRepository,
    onOpenReader: (String) -> Unit,
    onOpenGallery: (String) -> Unit,
    onOpenMusic: (String) -> Unit,
    onOpenVideo: (String) -> Unit,
) {
    val tabs = RecommendType.entries
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(MonoPageBg)) {
        MonoTopBar(title = "MONO") {
            IconButton(onClick = { onOpenMusic("music-1") }) {
                Icon(Icons.Outlined.GraphicEq, contentDescription = "播放器", tint = Color.White)
            }
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MonoNavDark,
            contentColor = Color.White,
            edgePadding = 0.dp,
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(tab.title) },
                )
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            RecommendationList(
                items = repository.recommendations(tabs[page], page = 1),
                onOpenReader = onOpenReader,
                onOpenGallery = onOpenGallery,
                onOpenMusic = onOpenMusic,
                onOpenVideo = onOpenVideo,
            )
        }
    }
}

@Composable
private fun RecommendationList(
    items: List<com.xumaohuai.mono.android.model.MonoItem>,
    onOpenReader: (String) -> Unit,
    onOpenGallery: (String) -> Unit,
    onOpenMusic: (String) -> Unit,
    onOpenVideo: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            LoadingStrip(text = "下拉刷新 / 上拉加载更多")
        }
        items(items, key = { it.id }) { item ->
            RecommendCard(
                item = item,
                onOpenReader = onOpenReader,
                onOpenGallery = onOpenGallery,
                onOpenMusic = onOpenMusic,
                onOpenVideo = onOpenVideo,
            )
        }
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun LoadingStrip(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
        color = Color(0xFF7D858B),
    )
}
