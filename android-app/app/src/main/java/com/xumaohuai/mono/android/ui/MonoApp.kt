package com.xumaohuai.mono.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xumaohuai.mono.android.data.MonoRepository
import com.xumaohuai.mono.android.ui.screens.GalleryScreen
import com.xumaohuai.mono.android.ui.screens.MusicScreen
import com.xumaohuai.mono.android.ui.screens.ReaderScreen
import com.xumaohuai.mono.android.ui.screens.RootShell
import com.xumaohuai.mono.android.ui.screens.VideoScreen

@Composable
fun MonoApp(repository: MonoRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.Home.value) {
        composable(Route.Home.value) {
            RootShell(
                repository = repository,
                onOpenReader = { navController.navigate(Route.Reader.create(it)) },
                onOpenGallery = { navController.navigate(Route.Gallery.create(it)) },
                onOpenMusic = { navController.navigate(Route.Music.create(it)) },
                onOpenVideo = { navController.navigate(Route.Video.create(it)) },
            )
        }
        detail(Route.Reader.value) { itemId ->
            ReaderScreen(item = repository.item(itemId), onBack = navController::popBackStack)
        }
        detail(Route.Gallery.value) { itemId ->
            GalleryScreen(item = repository.item(itemId), onBack = navController::popBackStack)
        }
        detail(Route.Music.value) { itemId ->
            MusicScreen(item = repository.item(itemId), onBack = navController::popBackStack)
        }
        detail(Route.Video.value) { itemId ->
            VideoScreen(item = repository.item(itemId), onBack = navController::popBackStack)
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.detail(
    route: String,
    content: @Composable (String) -> Unit,
) {
    composable(
        route = route,
        arguments = listOf(navArgument("itemId") { type = NavType.StringType }),
    ) { backStackEntry ->
        content(backStackEntry.arguments?.getString("itemId").orEmpty())
    }
}
