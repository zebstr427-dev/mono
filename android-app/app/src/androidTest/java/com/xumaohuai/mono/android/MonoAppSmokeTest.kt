package com.xumaohuai.mono.android

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.xumaohuai.mono.android.data.MockMonoRepository
import com.xumaohuai.mono.android.ui.MonoApp
import com.xumaohuai.mono.android.ui.theme.MonoTheme
import org.junit.Rule
import org.junit.Test

class MonoAppSmokeTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun bottomTabsAndRecommendTabsAreVisible() {
        composeRule.setContent {
            MonoTheme {
                MonoApp(repository = MockMonoRepository())
            }
        }

        composeRule.onNodeWithText("推荐").assertIsDisplayed()
        composeRule.onNodeWithText("发现").assertIsDisplayed()
        composeRule.onNodeWithText("社区").assertIsDisplayed()
        composeRule.onNodeWithText("我的").assertIsDisplayed()
        composeRule.onNodeWithText("早午茶").assertIsDisplayed()
        composeRule.onNodeWithText("音乐").assertIsDisplayed()
    }

    @Test
    fun discoverTabShowsBannerAndEmptyState() {
        composeRule.setContent {
            MonoTheme {
                MonoApp(repository = MockMonoRepository())
            }
        }

        composeRule.onNodeWithText("发现").performClick()
        composeRule.onNodeWithText("今日推荐").assertIsDisplayed()
        composeRule.onNodeWithText("发现页列表区域在原 iOS 项目中尚未实现，这里保留空态。").assertIsDisplayed()
    }
}
