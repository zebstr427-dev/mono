package com.xumaohuai.mono.android.ui

sealed class Route(val value: String) {
    data object Home : Route("home")
    data object Reader : Route("reader/{itemId}") {
        fun create(itemId: String) = "reader/$itemId"
    }
    data object Gallery : Route("gallery/{itemId}") {
        fun create(itemId: String) = "gallery/$itemId"
    }
    data object Music : Route("music/{itemId}") {
        fun create(itemId: String) = "music/$itemId"
    }
    data object Video : Route("video/{itemId}") {
        fun create(itemId: String) = "video/$itemId"
    }
}
