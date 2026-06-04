package com.xumaohuai.mono.android

import com.xumaohuai.mono.android.data.MockMonoRepository
import com.xumaohuai.mono.android.model.MeowType
import com.xumaohuai.mono.android.model.RecommendType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MonoRepositoryTest {
    private val repository = MockMonoRepository()

    @Test
    fun recommendTypesKeepIosOrderAndLabels() {
        val labels = RecommendType.entries.map { it.title }

        assertEquals(listOf("早午茶", "我的关注", "猜你喜欢", "视频", "音乐", "画册"), labels)
    }

    @Test
    fun meowTypesKeepIosRawValues() {
        assertEquals(1, MeowType.Tea.rawValue)
        assertEquals(2, MeowType.ImageBg.rawValue)
        assertEquals(3, MeowType.Images.rawValue)
        assertEquals(4, MeowType.Read.rawValue)
        assertEquals(5, MeowType.ReadThird.rawValue)
        assertEquals(7, MeowType.Video.rawValue)
        assertEquals(8, MeowType.Music.rawValue)
        assertEquals(9, MeowType.Pictures.rawValue)
    }

    @Test
    fun everyRecommendCategoryHasAtLeastThreeMockItems() {
        RecommendType.entries.forEach { type ->
            assertTrue("${type.title} should have at least three items", repository.recommendations(type, page = 1).size >= 3)
        }
    }

    @Test
    fun mockDataCoversAllRenderableMeowTypes() {
        val types = RecommendType.entries
            .flatMap { repository.recommendations(it, page = 1) }
            .map { it.meowType }
            .toSet()

        assertTrue(types.containsAll(MeowType.entries.toSet()))
    }
}
