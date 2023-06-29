package test.app.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import test.app.todocompose.ui.screens.splash.SplashScreen
import test.app.todocompose.util.Constants

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}