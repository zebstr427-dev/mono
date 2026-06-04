package com.xumaohuai.mono.android.media

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MediaUiState(
    val title: String = "",
    val subtitle: String = "",
    val isPlaying: Boolean = false,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
)

class MediaControllerViewModel : ViewModel() {
    private val mutableState = MutableStateFlow(MediaUiState())
    val state: StateFlow<MediaUiState> = mutableState.asStateFlow()

    fun load(title: String, subtitle: String, durationSeconds: Int) {
        mutableState.value = MediaUiState(
            title = title,
            subtitle = subtitle,
            durationMs = durationSeconds * 1000L,
        )
    }

    fun setPlaying(isPlaying: Boolean) {
        mutableState.value = mutableState.value.copy(isPlaying = isPlaying)
    }

    fun setProgress(positionMs: Long, durationMs: Long) {
        mutableState.value = mutableState.value.copy(
            positionMs = positionMs.coerceAtLeast(0L),
            durationMs = durationMs.coerceAtLeast(0L),
        )
    }
}
