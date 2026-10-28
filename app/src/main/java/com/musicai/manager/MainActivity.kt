package com.musicaimanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MusicAIDashboard()
            }
        }
    }
}

@Composable
fun MusicAIDashboard() {
    var songTitle by remember { mutableStateOf("") }
    var songDescription by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("آماده مدیریت کانال") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Music AI Manager",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("مدیریت کانال موسیقی هوش مصنوعی")

            OutlinedTextField(
                value = songTitle,
                onValueChange = { songTitle = it },
                label = { Text("عنوان آهنگ") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = songDescription,
                onValueChange = { songDescription = it },
                label = { Text("توضیحات ویدیو") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    status = if (songTitle.isBlank()) {
                        "ابتدا عنوان آهنگ را وارد کن."
                    } else {
                        "اطلاعات آهنگ آماده بررسی است."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("بررسی اطلاعات آهنگ")
            }

            HorizontalDivider()

            Text("وضعیت: $status")

            Text(
                "انتشار ویدیو تا زمان اتصال YouTube API و تأیید شما انجام نمی‌شود."
            )
        }
    }
}