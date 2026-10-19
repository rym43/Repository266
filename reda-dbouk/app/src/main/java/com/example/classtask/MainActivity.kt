package com.example.classtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.classtask.ui.theme.ClasstaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClasstaskTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
                    val tabs = listOf("Events", "Tasks")
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .consumeWindowInsets(WindowInsets.navigationBars)
                        ) {
                            when (selectedTab) {
                                0 -> EventsRoute()
                                else -> TasksRoute()
                            }
                        }
                        PrimaryTabRow(
                            selectedTabIndex = selectedTab,
                            modifier = Modifier.navigationBarsPadding()
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { Text(title) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
