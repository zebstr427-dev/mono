package com.xumaohuai.mono.android.model

enum class RecommendType(val title: String) {
    Tea("早午茶"),
    Attention("我的关注"),
    Like("猜你喜欢"),
    Video("视频"),
    Music("音乐"),
    Picture("画册"),
}

enum class MeowType(val rawValue: Int) {
    Tea(1),
    ImageBg(2),
    Images(3),
    Read(4),
    ReadThird(5),
    Video(7),
    Music(8),
    Pictures(9),
}

data class MonoImage(
    val url: String,
    val width: Int = 1200,
    val height: Int = 800,
    val description: String = "",
)

data class MonoUser(
    val name: String,
    val avatarUrl: String,
)

data class MonoItem(
    val id: String,
    val meowType: MeowType,
    val title: String,
    val description: String,
    val text: String = "",
    val author: String = "",
    val user: MonoUser,
    val category: String,
    val thumb: MonoImage,
    val images: List<MonoImage> = emptyList(),
    val musicUrl: String? = null,
    val songName: String = "",
    val artist: String = "",
    val musicDurationSeconds: Int = 0,
    val videoUrl: String? = null,
    val videoDurationSeconds: Int = 0,
    val recUrl: String? = null,
    val detailHtml: String = "",
    val bangCount: Int = 0,
    val commentCount: Int = 0,
)

data class DiscoverBanner(
    val id: String,
    val imageUrl: String,
    val title: String,
    val subtitle: String,
)

data class DiscoverModel(
    val banners: List<DiscoverBanner>,
)
