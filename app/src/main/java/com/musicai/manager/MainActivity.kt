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

    var generatedTitle by remember { mutableStateOf("") }
    var generatedDescription by remember { mutableStateOf("") }
    var generatedHashtags by remember { mutableStateOf("") }
    var generatedTags by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = songDescription,
                onValueChange = { songDescription = it },
                label = { Text("توضیحات آهنگ (اختیاری)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    val title = songTitle.trim()
                    val details = songDescription.trim()

                    if (title.isBlank()) {
                        status = "ابتدا عنوان آهنگ را وارد کن."
                        return@Button
                    }

                    generatedTitle = "$title | AI Music"

                    generatedDescription = buildString {
                        appendLine(title)
                        appendLine()
                        appendLine(
                            details.ifBlank {
                                "Enjoy this AI-generated music track."
                            }
                        )
                        appendLine()
                        appendLine("اگر از این آهنگ خوشت آمد، ویدیو را لایک کن.")
                        appendLine("برای آهنگ‌های جدید کانال را دنبال کن!")
                        appendLine()
                        append("#AIMusic #Music #NewMusic")
                    }

                    generatedHashtags =
                        "#AIMusic #Music #NewMusic #AIGeneratedMusic"

                    generatedTags = listOf(
                        title,
                        "AI music",
                        "AI generated music",
                        "new music",
                        "original music",
                        "music video"
                    ).joinToString(", ")

                    status = "پیش‌نویس آماده شد؛ قبل از استفاده بررسی کن."
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("✨ آماده‌سازی اطلاعات ویدیو")
            }

            HorizontalDivider()

            Text(
                text = "وضعیت: $status",
                style = MaterialTheme.typography.bodyLarge
            )

            if (generatedTitle.isNotBlank()) {
                Text(
                    "۱. عنوان پیشنهادی",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = generatedTitle,
                    onValueChange = { generatedTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "۲. توضیحات ویدیو",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = generatedDescription,
                    onValueChange = { generatedDescription = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5
                )

                Text(
                    "۳. هشتگ‌ها",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = generatedHashtags,
                    onValueChange = { generatedHashtags = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Text(
                    "۴. تگ‌های پیشنهادی",
                    style = MaterialTheme.typography.titleMedium
                )

                OutlinedTextField(
                    value = generatedTags,
                    onValueChange = { generatedTags = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Text(
                    text = "این اطلاعات فقط پیش‌نویس هستند. " +
                        "آن‌ها را بررسی و در صورت نیاز ویرایش کن.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            HorizontalDivider()

            Text(
                text = "انتشار خودکار هنوز فعال نیست. " +
                    "برای انتشار واقعی باید اتصال امن YouTube API " +
                    "اضافه شود و تأیید تو دریافت گردد.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}