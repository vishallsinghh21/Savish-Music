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
import androidx.compose.material3.SnackbarHostState
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

private data class SavishPlatform(val name: String, val accent: Color)

@Composable
fun HomeScreen(navController: NavController, snackbarHostState: SnackbarHostState) {
    val platforms = remember {
        listOf(
            SavishPlatform("YouTube", Color(0xFFFF0033)),
            SavishPlatform("Spotify", Color(0xFF1DB954)),
            SavishPlatform("JioSaavn", Color(0xFFFF9500))
        )
    }
    var selectedName by remember { mutableStateOf<String?>(null) }
    val selectedAccent = platforms.firstOrNull { it.name == selectedName }?.accent ?: Color(0xFFFF9500)
    val aura by animateColorAsState(selectedAccent.copy(alpha = .18f), label = "savishAura")

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SAVISH", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text("M U S I C", color = Color(0xFFFF9500), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))
            Text("Your music. Your platforms.", color = Color.White.copy(alpha = .65f), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(30.dp))

            platforms.forEach { platform ->
                val active = selectedName == platform.name
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (active) aura else Color.Transparent)
                        .background(
                            if (active) platform.accent.copy(alpha = .16f) else Color.White.copy(alpha = .05f),
                            RoundedCornerShape(50)
                        )
                        .border(
                            2.dp,
                            if (active) platform.accent else Color.White.copy(alpha = .22f),
                            RoundedCornerShape(50)
                        )
                        .clickable { selectedName = platform.name }
                        .padding(horizontal = 24.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        platform.name,
                        color = if (active) platform.accent else Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Text(
                if (selectedName == null) "Tap a platform" else "$selectedName selected",
                color = selectedAccent,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
