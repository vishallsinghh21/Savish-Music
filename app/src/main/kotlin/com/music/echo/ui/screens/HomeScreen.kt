package echo.music.iad1tya.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.SnackbarHostState

private data class SavishPlatform(val name: String, val accent: Color)

@Composable
fun HomeScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    val platforms = remember {
        listOf(
            SavishPlatform("YouTube", Color(0xFFFF0033)),
            SavishPlatform("Spotify", Color(0xFF1DB954)),
            SavishPlatform("JioSaavn", Color(0xFFFF9500))
        )
    }
    var selected by remember { mutableStateOf(platforms.first()) }
    val aura by animateColorAsState(selected.accent.copy(alpha = 0.20f), label = "platformAura")

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SAVISH MUSIC",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Choose your music platform",
                color = Color.White.copy(alpha = .65f),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(28.dp))

            platforms.forEach { platform ->
                val active = selected.name == platform.name
                val fill = if (active) platform.accent.copy(alpha = .18f) else Color.White.copy(alpha = .05f)
                val borderColor = if (active) platform.accent else Color.White.copy(alpha = .22f)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (active) aura else Color.Transparent)
                        .background(fill, RoundedCornerShape(50))
                        .border(2.dp, borderColor, RoundedCornerShape(50))
                        .clickable {
                            selected = platform
                            when (platform.name) {
                                "YouTube" -> navController.navigate("search")
                                "Spotify" -> navController.navigate("settings/spotify_import")
                                "JioSaavn" -> navController.navigate("search")
                            }
                        }
                        .padding(horizontal = 22.dp, vertical = 17.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = platform.name,
                        color = if (active) platform.accent else Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(28.dp))
            Text(
                text = "Tap a capsule to switch its color",
                color = selected.accent.copy(alpha = .9f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
