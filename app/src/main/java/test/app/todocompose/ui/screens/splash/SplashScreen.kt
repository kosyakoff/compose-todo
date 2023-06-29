package test.app.todocompose.ui.screens.splash

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.app.todocompose.R
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.ui.theme.splashScreenBackgroundColor
import test.app.todocompose.util.Constants

@Composable
fun SplashScreen(startAnim: Boolean = false, navigateToListScreen: () -> Unit) {

    var startAnimation by remember {
        mutableStateOf(startAnim)
    }

    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) {
            0.dp
        } else {
            100.dp
        },
        animationSpec = tween(durationMillis = Constants.SPLASH_SCREEN_LOGO_ANIMATION_DURATION)
    )

    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) {
            1f
        } else {
            0f
        },
        animationSpec = tween(durationMillis = Constants.SPLASH_SCREEN_LOGO_ANIMATION_DURATION)
    )

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true, block = {
        scope.launch {
            startAnimation = true
            delay(Constants.SPLASH_SCREEN_DELAY)
            navigateToListScreen()
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.splashScreenBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getLogo()),
            contentDescription = null,
            modifier = Modifier
                .size(Dimensions.APP_LOGO_SIZE)
                .offset(y = offsetState)
                .alpha(alphaState)
        )
    }
}

@Composable
fun getLogo(): Int {
    return if (isSystemInDarkTheme()) {
        R.drawable.logo_dark
    } else {
        R.drawable.logo_light
    }
}

@Composable
@Preview(name = Constants.PREVIEW_DEFAULT)
@Preview(
    name = Constants.PREVIEW_DARK_MODE,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun SplashScreenPreview() {
    SplashScreen(startAnim = true) {}
}