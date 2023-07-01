package test.app.todocompose.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import test.app.todocompose.ui.screens.splash.SplashScreen
import test.app.todocompose.util.Constants

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(route = Constants.SPLASH_SCREEN, exitTransition = {
        fadeOut(animationSpec = tween(durationMillis = Constants.SCREEN_TRANSITION_ANIMATION_LENGTH))
    }) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}