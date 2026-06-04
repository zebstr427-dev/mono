package com.xumaohuai.mono.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.xumaohuai.mono.android.R
import com.xumaohuai.mono.android.data.MonoRepository
import com.xumaohuai.mono.android.ui.theme.MonoNavDark
import com.xumaohuai.mono.android.ui.theme.MonoPageBg

private data class BottomTab(
    val title: String,
    val icon: Int,
    val selectedIcon: Int,
)

@Composable
fun RootShell(
    repository: MonoRepository,
    onOpenReader: (String) -> Unit,
    onOpenGallery: (String) -> Unit,
    onOpenMusic: (String) -> Unit,
    onOpenVideo: (String) -> Unit,
) {
    val tabs = listOf(
        BottomTab("推荐", R.drawable.tab_recommend, R.drawable.tab_recommend_active),
        BottomTab("发现", R.drawable.tab_explore, R.drawable.tab_explore_active),
        BottomTab("社区", R.drawable.tab_social, R.drawable.tab_social_active),
        BottomTab("我的", R.drawable.tab_mine, R.drawable.tab_mine_active),
    )
    var selected by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MonoNavDark) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = {
                            Image(
                                painter = painterResource(if (selected == index) tab.selectedIcon else tab.icon),
                                contentDescription = tab.title,
                            )
                        },
                        label = { Text(tab.title) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MonoPageBg)
                .padding(innerPadding),
        ) {
            when (selected) {
                0 -> RecommendScreen(
                    repository = repository,
                    onOpenReader = onOpenReader,
                    onOpenGallery = onOpenGallery,
                    onOpenMusic = onOpenMusic,
                    onOpenVideo = onOpenVideo,
                )
                1 -> DiscoverScreen(repository = repository)
                2 -> PlaceholderTab("社区", "原 iOS 仓库里 CommunityVC 目前是占位页。")
                3 -> PlaceholderTab("我的", "原 iOS 仓库里 MineVC 目前是占位页。")
            }
        }
    }
}
