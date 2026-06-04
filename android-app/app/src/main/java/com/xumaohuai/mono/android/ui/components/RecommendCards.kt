package com.xumaohuai.mono.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xumaohuai.mono.android.model.MeowType
import com.xumaohuai.mono.android.model.MonoItem
import com.xumaohuai.mono.android.ui.theme.MonoMuted
import com.xumaohuai.mono.android.ui.theme.MonoText

@Composable
fun RecommendCard(
    item: MonoItem,
    onOpenReader: (String) -> Unit,
    onOpenGallery: (String) -> Unit,
    onOpenMusic: (String) -> Unit,
    onOpenVideo: (String) -> Unit,
) {
    val click = when (item.meowType) {
        MeowType.Read, MeowType.ReadThird -> ({ onOpenReader(item.id) })
        MeowType.Images, MeowType.Pictures -> ({ onOpenGallery(item.id) })
        MeowType.Music -> ({ onOpenMusic(item.id) })
        MeowType.Video -> ({ onOpenVideo(item.id) })
        MeowType.Tea, MeowType.ImageBg -> ({ onOpenReader(item.id) })
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = click)
            .padding(top = 16.dp),
    ) {
        CardTitle(item)
        when (item.meowType) {
            MeowType.Tea -> TeaBody(item)
            MeowType.ImageBg -> ImageBgBody(item)
            MeowType.Images -> ImagesBody(item)
            MeowType.Read, MeowType.ReadThird -> ReadBody(item)
            MeowType.Video -> VideoBody(item)
            MeowType.Music -> MusicBody(item)
            MeowType.Pictures -> PicturesBody(item)
        }
        BottomActions(item)
    }
}

@Composable
private fun CardTitle(item: MonoItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color(0xFF212124)),
            contentAlignment = Alignment.Center,
        ) {
            Text("M", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        Column(modifier = Modifier.padding(start = 10.dp).weight(1f)) {
            Text(item.user.name, color = MonoText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(item.category, color = MonoMuted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun TeaBody(item: MonoItem) {
    Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
        Text(item.title, color = MonoText, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(item.text, color = MonoMuted, fontSize = 15.sp, lineHeight = 22.sp)
        Spacer(Modifier.height(14.dp))
        MonoRemoteImage(item.thumb)
    }
}

@Composable
private fun ImageBgBody(item: MonoItem) {
    Box(modifier = Modifier.padding(top = 14.dp)) {
        MonoRemoteImage(item.thumb, modifier = Modifier.fillMaxWidth())
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color(0x99000000))
                .padding(18.dp),
        ) {
            Text(item.text, color = Color.White, fontSize = 22.sp, lineHeight = 30.sp)
            if (item.author.isNotBlank()) {
                Text("---${item.author}", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImagesBody(item: MonoItem) {
    Column(modifier = Modifier.padding(18.dp)) {
        Headline(item)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(top = 12.dp),
        ) {
            item.images.take(4).forEach { image ->
                MonoRemoteImage(
                    image = image,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                )
            }
        }
    }
}

@Composable
private fun ReadBody(item: MonoItem) {
    Column(modifier = Modifier.padding(18.dp)) {
        MonoRemoteImage(item.thumb)
        Spacer(Modifier.height(14.dp))
        Headline(item)
    }
}

@Composable
private fun VideoBody(item: MonoItem) {
    Box(modifier = Modifier.padding(top = 14.dp)) {
        MonoRemoteImage(item.thumb)
        Icon(
            Icons.Outlined.PlayCircle,
            contentDescription = "播放视频",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp),
        )
        Text(
            formatSeconds(item.videoDurationSeconds),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(14.dp)
                .background(Color(0x99000000))
                .padding(horizontal = 8.dp, vertical = 3.dp),
        )
    }
    Column(modifier = Modifier.padding(18.dp)) {
        Headline(item)
    }
}

@Composable
private fun MusicBody(item: MonoItem) {
    Column(modifier = Modifier.padding(18.dp)) {
        Box(contentAlignment = Alignment.Center) {
            MonoRemoteImage(item.thumb, modifier = Modifier.aspectRatio(1f))
            Icon(
                Icons.Outlined.MusicNote,
                contentDescription = "播放音乐",
                tint = Color.White,
                modifier = Modifier
                    .size(68.dp)
                    .background(Color(0x88000000), CircleShape)
                    .padding(12.dp),
            )
        }
        Spacer(Modifier.height(12.dp))
        Headline(item)
        Text("${item.songName} - ${item.artist}", color = MonoMuted, fontSize = 13.sp)
    }
}

@Composable
private fun PicturesBody(item: MonoItem) {
    Box(modifier = Modifier.padding(top = 14.dp)) {
        MonoRemoteImage(item.thumb)
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(14.dp)
                .background(Color(0x99000000))
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Outlined.PhotoLibrary, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Text("${item.images.size}张图片", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(start = 5.dp))
        }
    }
    Column(modifier = Modifier.padding(18.dp)) {
        Headline(item)
    }
}

@Composable
private fun Headline(item: MonoItem) {
    Text(item.title, color = MonoText, fontSize = 20.sp, fontWeight = FontWeight.Bold, lineHeight = 27.sp)
    if (item.description.isNotBlank()) {
        Spacer(Modifier.height(7.dp))
        Text(
            item.description,
            color = MonoMuted,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun BottomActions(item: MonoItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Action(Icons.Outlined.FavoriteBorder, "${item.bangCount}")
        Action(Icons.Outlined.ChatBubbleOutline, "${item.commentCount}")
        Action(Icons.Outlined.IosShare, "分享")
    }
}

@Composable
private fun Action(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = text, tint = MonoMuted, modifier = Modifier.size(18.dp))
        Text(text, color = MonoMuted, fontSize = 12.sp, modifier = Modifier.padding(start = 5.dp))
    }
}

private fun formatSeconds(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60
    return "%02d:%02d".format(min, sec)
}
