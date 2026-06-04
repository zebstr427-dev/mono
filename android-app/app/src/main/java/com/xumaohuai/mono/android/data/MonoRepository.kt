package com.xumaohuai.mono.android.data

import com.xumaohuai.mono.android.model.DiscoverModel
import com.xumaohuai.mono.android.model.MonoItem
import com.xumaohuai.mono.android.model.RecommendType

interface MonoRepository {
    fun recommendations(type: RecommendType, page: Int): List<MonoItem>
    fun discover(): DiscoverModel
    fun item(id: String): MonoItem?
}
