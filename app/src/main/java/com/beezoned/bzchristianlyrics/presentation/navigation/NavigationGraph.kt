package com.beezoned.bzchristianlyrics.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.beezoned.bzchristianlyrics.presentation.genres.GenreDetailScreen
import com.beezoned.bzchristianlyrics.presentation.genres.GenresScreen
import com.beezoned.bzchristianlyrics.presentation.home.HomeScreen
import com.beezoned.bzchristianlyrics.presentation.settings.SettingsScreen
import com.beezoned.bzchristianlyrics.presentation.songdetail.SongDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Genres : Screen("genres")
    object GenreDetail : Screen("genre_detail/{genreName}")
    object Settings : Screen("settings")
    object SongDetail : Screen("song_detail/{songId}")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Genres.route) {
            GenresScreen(navController = navController)
        }
        composable(
            route = Screen.GenreDetail.route,
            arguments = listOf(navArgument("genreName") { type = NavType.StringType })
        ) { backStackEntry ->
            val genreName = backStackEntry.arguments?.getString("genreName") ?: ""
            GenreDetailScreen(genreName = genreName,navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = Screen.SongDetail.route,
            arguments = listOf(navArgument("songId") { type = NavType.StringType })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            SongDetailScreen(songId = songId, navController = navController)
        }
    }
}