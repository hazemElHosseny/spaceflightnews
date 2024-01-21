package net.spaceflightnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import net.spaceflightnews.articles.presentation.ArticlesScreen
import net.spaceflightnews.designsystem.theme.SpaceflightNewsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceflightNewsTheme {
                ArticlesScreen()
            }
        }
    }
}
