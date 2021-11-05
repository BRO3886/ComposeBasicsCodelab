package dev.sidv.composebasicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sidv.composebasicscodelab.ui.theme.ComposeBasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
private fun MyApp() {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnboarding) {
        OnboardingScreen(
            onContinueClicked = { shouldShowOnboarding = !shouldShowOnboarding }
        )
    } else Greetings()
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Welcome to the basics Codelab")
            Button(
                onClick = onContinueClicked,
                modifier = Modifier.padding(vertical = 24.dp),
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
private fun Greeting(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }
//    val extraPadding by animateDpAsState(
//        targetValue = if (expanded) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .shadow(elevation = 4.dp,shape = RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .animateContentSize(
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hello",
                )
                Text(
                    text = "$name!",
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ut bibendum quam. Phasellus ornare vel orci quis efficitur. Curabitur feugiat ante in justo pharetra, ut mollis odio finibus. Fusce id sollicitudin metus, a porta ipsum. Aenean congue dolor lectus. Proin rhoncus euismod diam in dignissim.",
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) "Show less" else "Show more"
                )
            }
        }
    }
}

@Composable
fun SystemUi(windows: Window) =
    MaterialTheme {
        windows.statusBarColor = MaterialTheme.colors.surface.toArgb()
        windows.navigationBarColor = MaterialTheme.colors.surface.toArgb()

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION")
        if (MaterialTheme.colors.surface.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

@Preview(showBackground = true, name = "onboarding", widthDp = 320, heightDp = 600)
@Composable
fun OnboardingPreview() {
    ComposeBasicsCodelabTheme {
        Surface(color = MaterialTheme.colors.background) {
            OnboardingScreen(onContinueClicked = { true })
        }
    }
}

@Preview(
    showBackground = true,
    name = "Greetings",
    widthDp = 320,
    heightDp = 600,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DefaultPreview() {
    ComposeBasicsCodelabTheme {
        Surface(color = MaterialTheme.colors.background) {
            Greetings()
        }
    }
}