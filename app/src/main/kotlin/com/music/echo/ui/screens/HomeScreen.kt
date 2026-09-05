package echo.music.iad1tya.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.music.innertube.models.Artist
import com.music.innertube.models.SongItem
import com.music.innertube.models.YTItem
import echo.music.iad1tya.viewmodels.HomeViewModel

private data class SavishPlatform(
    val name: String,
    val title: String,
    val auraColor: Color
) {
    companion object {
        val ALL_MEDIA = SavishPlatform("all_media", "All media", Color(0xFF18D7F5))
        val YOUTUBE = SavishPlatform("youtube", "YouTube", Color(0xFFFF2D55))
        val SPOTIFY = SavishPlatform("spotify", "Spotify", Color(0xFF1DB954))
        val JIOSAAVN = SavishPlatform("jiosaavn", "JioSaavn", Color(0xFFFF9500))
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var selectedPlatform by remember { mutableStateOf(SavishPlatform.ALL_MEDIA) }
    val auraColor = selectedPlatform.auraColor
    val homePage by viewModel.homePage.collectAsState()
    val quickPicks by viewModel.quickPicks.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val platforms = listOf(
        SavishPlatform.ALL_MEDIA,
        SavishPlatform.YOUTUBE,
        SavishPlatform.SPOTIFY,
        SavishPlatform.JIOSAAVN
    )

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF080C12), Color(0xFF101923), Color(0xFF07151A))
                    )
                )
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 96.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(Modifier.size(9.dp).clip(CircleShape).background(auraColor))
                                    Spacer(Modifier.width(7.dp))
                                    Text(
                                        "• GLOBAL SYNC ACTIVE",
                                        color = auraColor,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 1.1.sp
                                    )
                                }
                                Spacer(Modifier.height(5.dp))
                                Text(
                                    "Savish Music",
                                    color = Color.White,
                                    fontSize = 34.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                            Surface(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, auraColor.copy(alpha = .75f), CircleShape)
                                    .clickable { navController.navigate("settings") },
                                shape = CircleShape,
                                color = auraColor.copy(alpha = .12f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Edit, "Profile", tint = auraColor, modifier = Modifier.size(25.dp))
                                }
                            }
                        }

                        Spacer(Modifier.height(18.dp))
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            platforms.forEach { platform ->
                                val active = platform == selectedPlatform
                                val color = platform.auraColor
                                Surface(
                                    modifier = Modifier
                                        .height(44.dp)
                                        .clip(RoundedCornerShape(24.dp))
                                        .border(
                                            if (active) 2.dp else 1.dp,
                                            if (active) color else Color.White.copy(alpha = .13f),
                                            RoundedCornerShape(24.dp)
                                        )
                                        .clickable { selectedPlatform = platform },
                                    shape = RoundedCornerShape(24.dp),
                                    color = if (active) color.copy(alpha = .16f) else Color(0xFF171C23)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 18.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            platform.title,
                                            color = if (active) color else Color(0xFF9CA6B8),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(18.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .clip(RoundedCornerShape(28.dp))
                                .background(Color(0xFF20262E))
                                .border(1.3.dp, auraColor.copy(alpha = .35f), RoundedCornerShape(28.dp))
                                .padding(horizontal = 17.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, null, tint = auraColor, modifier = Modifier.size(23.dp))
                            BasicTextField(
                                value = searchText,
                                onValueChange = { searchText = it },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp),
                                textStyle = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                                decorationBox = { inner ->
                                    if (searchText.isEmpty()) {
                                        Text(
                                            "Search songs in Savish ${selectedPlatform.title}…",
                                            color = Color(0xFF9EA8B8),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    inner()
                                }
                            )
                        }

                        Spacer(Modifier.height(18.dp))
                        Text("Welcome back,", color = Color(0xFF8E9BAE), fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                if (!homePage?.sections.isNullOrEmpty()) {
                    items(homePage!!.sections.take(8), key = { it.title + it.items.size }) { section ->
                        SavishSection(
                            title = section.title,
                            label = section.label,
                            items = section.items,
                            accent = auraColor,
                            onItemClick = { item ->
                                if (item is SongItem) navController.navigate("watch/${item.id}")
                            }
                        )
                    }
                } else if (!quickPicks.isNullOrEmpty()) {
                    item {
                        SavishSection(
                            title = "New releases",
                            label = null,
                            items = quickPicks!!.map { song ->
                                SongItem(song.id, song.title, song.artists.map { Artist(it.name, it.id) }, thumbnail = song.thumbnailUrl ?: "")
                            },
                            accent = auraColor,
                            onItemClick = { }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SavishSection(
    title: String,
    label: String?,
    items: List<YTItem>,
    accent: Color,
    onItemClick: (YTItem) -> Unit
) {
    if (items.isEmpty()) return
    Column(Modifier.fillMaxWidth().padding(top = 20.dp)) {
        if (!label.isNullOrBlank()) {
            Text(
                label.uppercase(),
                modifier = Modifier.padding(horizontal = 18.dp),
                color = Color(0xFFB9C1CF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = .8.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, color = accent, fontSize = 29.sp, fontWeight = FontWeight.Black)
            Text("›", color = accent, fontSize = 34.sp, fontWeight = FontWeight.Light)
        }
        Spacer(Modifier.height(12.dp))
        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.distinctBy { it.id }, key = { it.id }) { item ->
                Column(modifier = Modifier.width(176.dp).clickable { onItemClick(item) }) {
                    AsyncImage(
                        model = item.thumbnail,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(176.dp).clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        item.title,
                        color = Color(0xFFE9EDF4),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item is SongItem) {
                        Text(
                            item.artists.joinToString(", ") { it.name },
                            color = Color(0xFFA8B1BF),
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
