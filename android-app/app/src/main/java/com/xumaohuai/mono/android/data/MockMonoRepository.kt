package com.xumaohuai.mono.android.data

import com.xumaohuai.mono.android.model.DiscoverBanner
import com.xumaohuai.mono.android.model.DiscoverModel
import com.xumaohuai.mono.android.model.MeowType
import com.xumaohuai.mono.android.model.MonoImage
import com.xumaohuai.mono.android.model.MonoItem
import com.xumaohuai.mono.android.model.MonoUser
import com.xumaohuai.mono.android.model.RecommendType

class MockMonoRepository : MonoRepository {
    private val user = MonoUser(
        name = "MONO 编辑部",
        avatarUrl = image(10),
    )

    private val allItems = buildList {
        add(
            item(
                id = "tea-1",
                type = MeowType.Tea,
                title = "早午茶：把日常重新摆进光里",
                description = "今日精选从一杯茶开始，音乐、图像和一段短读物都刚刚好。",
                category = "早午茶",
                thumb = imageModel(21, 1200, 900),
                text = "早晨适合慢一点。给自己留十分钟，读完这组温柔的片段。",
            ),
        )
        add(
            item(
                id = "image-bg-1",
                type = MeowType.ImageBg,
                title = "雨后城市里的蓝色窗户",
                description = "一张大图、一句短诗，保留原 iOS 版本里清爽又克制的卡片感。",
                category = "影像",
                thumb = imageModel(31, 900, 1200),
                text = "那些蓝色的窗户像刚刚醒来的海。",
                author = "阿北",
            ),
        )
        add(
            item(
                id = "images-1",
                type = MeowType.Images,
                title = "咖啡馆里的六种安静",
                description = "多图卡片用于复刻原项目的 `RecommendImagesCell`。",
                category = "图集",
                thumb = imageModel(41, 1200, 800),
                images = listOf(imageModel(41), imageModel(42), imageModel(43), imageModel(44)),
                text = "木桌、手写菜单、玻璃杯上的水汽，还有窗边的一小块阳光。",
            ),
        )
        add(
            item(
                id = "read-1",
                type = MeowType.Read,
                title = "一个人散步时会想起什么",
                description = "阅读卡片保留标题、摘要、配图和底部互动区域。",
                category = "阅读",
                thumb = imageModel(51, 1200, 780),
                text = "散步不是为了抵达哪里，而是让脑子里的线慢慢解开。",
                detailHtml = articleHtml("一个人散步时会想起什么"),
            ),
        )
        add(
            item(
                id = "read-third-1",
                type = MeowType.ReadThird,
                title = "第三方阅读模式的另一种实现",
                description = "原 iOS 使用两个 WKWebView 切换阅读模式；Android 首版用 WebView 承载 Mock HTML。",
                category = "阅读",
                thumb = imageModel(61, 1200, 820),
                text = "这个页面用于验证详情页、WebView 容器和返回栈。",
                recUrl = "https://example.com/mono/read-third",
                detailHtml = articleHtml("第三方阅读模式的另一种实现"),
            ),
        )
        add(
            item(
                id = "video-1",
                type = MeowType.Video,
                title = "四十秒的海边黄昏",
                description = "视频卡片对应原项目 `RecommendVideoCell`，首版播放公开示例视频。",
                category = "视频",
                thumb = imageModel(71, 1280, 720),
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                videoDurationSeconds = 40,
            ),
        )
        add(
            item(
                id = "music-1",
                type = MeowType.Music,
                title = "午后循环播放的一首歌",
                description = "音乐卡片复刻唱片封面、播放按钮、歌手和时长。",
                category = "音乐",
                thumb = imageModel(81, 1000, 1000),
                musicUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
                songName = "Soft Loop",
                artist = "SoundHelix",
                musicDurationSeconds = 225,
            ),
        )
        add(
            item(
                id = "pictures-1",
                type = MeowType.Pictures,
                title = "画册：白墙、绿植与玻璃",
                description = "画册卡片用于全屏图片浏览体验。",
                category = "画册",
                thumb = imageModel(91, 1200, 900),
                images = listOf(imageModel(91), imageModel(92), imageModel(93), imageModel(94), imageModel(95)),
            ),
        )
    }

    private val byType = mapOf(
        RecommendType.Tea to listOf("tea-1", "read-1", "image-bg-1"),
        RecommendType.Attention to listOf("images-1", "read-third-1", "music-1"),
        RecommendType.Like to listOf("image-bg-1", "read-1", "pictures-1"),
        RecommendType.Video to listOf("video-1", "video-1", "read-third-1"),
        RecommendType.Music to listOf("music-1", "music-1", "read-1"),
        RecommendType.Picture to listOf("pictures-1", "images-1", "image-bg-1"),
    )

    override fun recommendations(type: RecommendType, page: Int): List<MonoItem> {
        val lookup = allItems.associateBy { it.id }
        return byType.getValue(type)
            .mapIndexed { index, id ->
                val base = lookup.getValue(id)
                if (index == 0 || page == 1) base else base.copy(id = "${base.id}-p$page-$index")
            }
    }

    override fun discover(): DiscoverModel = DiscoverModel(
        banners = listOf(
            DiscoverBanner("banner-1", image(101), "今日推荐", "一组由图片、声音和短文组成的灵感入口"),
            DiscoverBanner("banner-2", image(102), "热门画册", "看见城市里细碎而发亮的地方"),
            DiscoverBanner("banner-3", image(103), "音乐时间", "把播放键交给下午"),
        ),
    )

    override fun item(id: String): MonoItem? {
        val cleanId = id.substringBefore("-p")
        return allItems.firstOrNull { it.id == cleanId }
    }

    private fun item(
        id: String,
        type: MeowType,
        title: String,
        description: String,
        category: String,
        thumb: MonoImage,
        text: String = "",
        author: String = "",
        images: List<MonoImage> = emptyList(),
        musicUrl: String? = null,
        songName: String = "",
        artist: String = "",
        musicDurationSeconds: Int = 0,
        videoUrl: String? = null,
        videoDurationSeconds: Int = 0,
        recUrl: String? = null,
        detailHtml: String = "",
    ) = MonoItem(
        id = id,
        meowType = type,
        title = title,
        description = description,
        text = text,
        author = author,
        user = user,
        category = category,
        thumb = thumb,
        images = images,
        musicUrl = musicUrl,
        songName = songName,
        artist = artist,
        musicDurationSeconds = musicDurationSeconds,
        videoUrl = videoUrl,
        videoDurationSeconds = videoDurationSeconds,
        recUrl = recUrl,
        detailHtml = detailHtml,
        bangCount = 120 + id.length,
        commentCount = 18 + id.length,
    )

    private fun imageModel(seed: Int, width: Int = 1200, height: Int = 800) = MonoImage(
        url = image(seed),
        width = width,
        height = height,
        description = "MONO mock image $seed",
    )

    private fun image(seed: Int) = "https://picsum.photos/seed/mono-$seed/1200/900"

    private fun articleHtml(title: String) = """
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <style>
                body { font-family: sans-serif; padding: 22px; line-height: 1.8; color: #222; background: #f5f7f8; }
                h1 { font-size: 24px; }
                p { font-size: 16px; }
            </style>
        </head>
        <body>
            <h1>$title</h1>
            <p>这是 Android 首版内置的 Mock 阅读内容，用来复刻原 iOS 项目中阅读与第三方阅读详情页的基本体验。</p>
            <p>后续如果接入真实接口，只需要替换 Repository，不需要重写 UI。</p>
        </body>
        </html>
    """.trimIndent()
}
