package com.musicaimanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MusicAIManagerApp()
            }
        }
    }
}

@Composable
fun MusicAIManagerApp() {
    var page by rememberSaveable { mutableStateOf("home") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Music AI Manager",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("دستیار مدیریت کانال موسیقی هوش مصنوعی")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { page = "home" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("خانه")
                }

                Button(
                    onClick = { page = "video" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ویدیو")
                }

                Button(
                    onClick = { page = "ideas" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("ایده‌ها")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { page = "schedule" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("برنامه انتشار")
                }

                OutlinedButton(
                    onClick = { page = "analytics" },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("آمار کانال")
                }
            }

            HorizontalDivider()

            when (page) {
                "home" -> HomePage(
                    onVideo = { page = "video" },
                    onIdeas = { page = "ideas" },
                    onSchedule = { page = "schedule" },
                    onAnalytics = { page = "analytics" }
                )

                "video" -> VideoPage()

                "ideas" -> IdeasPage()

                "schedule" -> SchedulePage()

                "analytics" -> AnalyticsPage()

                else -> HomePage(
                    onVideo = { page = "video" },
                    onIdeas = { page = "ideas" },
                    onSchedule = { page = "schedule" },
                    onAnalytics = { page = "analytics" }
                )
            }

            HorizontalDivider()

            Text(
                text = "انتشار ویدیو بدون تأیید تو انجام نمی‌شود.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun HomePage(
    onVideo: () -> Unit,
    onIdeas: () -> Unit,
    onSchedule: () -> Unit,
    onAnalytics: () -> Unit
) {
    Text(
        "داشبورد مدیریت",
        style = MaterialTheme.typography.titleLarge
    )

    Text(
        "ابزارهای اصلی برای آماده‌سازی و مدیریت محتوای کانال."
    )

    Button(
        onClick = onVideo,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("🎵 آماده‌سازی اطلاعات ویدیو")
    }

    OutlinedButton(
        onClick = onIdeas,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("💡 دریافت ایده‌های آهنگ")
    }

    OutlinedButton(
        onClick = onSchedule,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("🗓️ برنامه‌ریزی انتشار")
    }

    OutlinedButton(
        onClick = onAnalytics,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("📊 بخش آمار و رشد کانال")
    }

    Text(
        "وضعیت اتصال: هنوز به YouTube API متصل نشده است.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun VideoPage() {
    var songTitle by rememberSaveable { mutableStateOf("") }
    var songDetails by rememberSaveable { mutableStateOf("") }

    var videoTitle by rememberSaveable { mutableStateOf("") }
    var videoDescription by rememberSaveable { mutableStateOf("") }
    var hashtags by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable { mutableStateOf("") }

    var status by rememberSaveable {
        mutableStateOf("عنوان آهنگ را وارد کن.")
    }

    var approved by rememberSaveable {
        mutableStateOf(false)
    }

    Text(
        "آماده‌سازی اطلاعات ویدیو",
        style = MaterialTheme.typography.titleLarge
    )

    OutlinedTextField(
        value = songTitle,
        onValueChange = {
            songTitle = it
            approved = false
        },
        label = { Text("عنوان آهنگ") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )

    OutlinedTextField(
        value = songDetails,
        onValueChange = {
            songDetails = it
            approved = false
        },
        label = { Text("توضیحات آهنگ") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 3
    )

    Button(
        onClick = {
            val title = songTitle.trim()

            if (title.isBlank()) {
                status = "ابتدا عنوان آهنگ را وارد کن."
            } else {
                videoTitle = "$title | AI Music"

                videoDescription = buildString {
                    appendLine(title)
                    appendLine()

                    appendLine(
                        songDetails.trim().ifBlank {
                            "Listen to this AI-generated music track."
                        }
                    )

                    appendLine()
                    appendLine(
                        "اگر از این آهنگ خوشت آمد، " +
                            "ویدیو را لایک کن و کانال را دنبال کن."
                    )

                    appendLine()
                    appendLine(
                        "Music created with AI-assisted tools."
                    )

                    appendLine()
                    append("#AIMusic #AIGeneratedMusic #Music")
                }

                hashtags =
                    "#AIMusic #AIGeneratedMusic #Music #NewMusic"

                tags = listOf(
                    title,
                    "AI music",
                    "AI generated music",
                    "original music",
                    "new music",
                    "music video"
                ).joinToString(", ")

                approved = false
                status = "پیش‌نویس آماده شد؛ آن را بررسی کن."
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("✨ ساخت پیش‌نویس")
    }

    if (videoTitle.isNotBlank()) {
        HorizontalDivider()

        Text(
            "عنوان پیشنهادی",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = videoTitle,
            onValueChange = {
                videoTitle = it
                approved = false
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "توضیحات ویدیو",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = videoDescription,
            onValueChange = {
                videoDescription = it
                approved = false
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Text(
            "هشتگ‌ها",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = hashtags,
            onValueChange = {
                hashtags = it
                approved = false
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "تگ‌های ویدیو",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = tags,
            onValueChange = {
                tags = it
                approved = false
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Button(
            onClick = {
                if (videoTitle.isBlank()) {
                    status = "عنوان ویدیو نباید خالی باشد."
                } else {
                    approved = true
                    status =
                        "پیش‌نویس تأیید شد؛ هنوز منتشر نشده است."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("✅ تأیید پیش‌نویس")
        }

        Text(
            if (approved) {
                "وضعیت: پیش‌نویس تأیید شده"
            } else {
                "وضعیت: پیش‌نویس تأیید نشده"
            }
        )

        Text(status)
    }
}

@Composable
fun IdeasPage() {
    var genre by rememberSaveable {
        mutableStateOf("موسیقی احساسی")
    }

    var result by rememberSaveable {
        mutableStateOf("")
    }

    val ideas = mapOf(
        "موسیقی احساسی" to listOf(
            "Dreams of the Night",
            "Memories in the Rain",
            "A Heart Without Words",
            "Lost in Beautiful Memories",
            "The Last Sunset"
        ),
        "موسیقی آرامش‌بخش" to listOf(
            "Peaceful Ocean",
            "Midnight Piano",
            "Quiet Mountain",
            "Rain and Dreams",
            "A Calm New Morning"
        ),
        "موسیقی سینمایی" to listOf(
            "Rise of the Forgotten",
            "Journey Beyond the Stars",
            "The Final Kingdom",
            "Echoes of Destiny",
            "A New Beginning"
        ),
        "موسیقی پرانرژی" to listOf(
            "Rise Above",
            "Electric Dreams",
            "Unstoppable Night",
            "Beyond the Limits",
            "Into the Future"
        )
    )

    Text(
        "ایده‌های محتوایی",
        style = MaterialTheme.typography.titleLarge
    )

    Text("سبک موسیقی را انتخاب کن.")

    listOf(
        "موسیقی احساسی",
        "موسیقی آرامش‌بخش",
        "موسیقی سینمایی",
        "موسیقی پرانرژی"
    ).forEach { option ->
        Row {
            RadioButton(
                selected = genre == option,
                onClick = { genre = option }
            )

            Text(
                text = option,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }

    Button(
        onClick = {
            result = ideas[genre]
                ?.mapIndexed { index, idea ->
                    "${index + 1}. $idea"
                }
                ?.joinToString("\n")
                ?: ""
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("تولید ایده")
    }

    if (result.isNotBlank()) {
        Text(
            "ایده‌های پیشنهادی",
            style = MaterialTheme.typography.titleMedium
        )

        Text(result)
    }

    Text(
        "این ایده‌ها از فهرست داخلی برنامه انتخاب می‌شوند؛ " +
            "هنوز از هوش مصنوعی آنلاین استفاده نمی‌شود.",
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun SchedulePage() {
    var videoName by rememberSaveable {
        mutableStateOf("")
    }

    var publishDate by rememberSaveable {
        mutableStateOf("")
    }

    var publishTime by rememberSaveable {
        mutableStateOf("")
    }

    var scheduleList by rememberSaveable {
        mutableStateOf("")
    }

    var status by rememberSaveable {
        mutableStateOf("برنامه انتشار هنوز ثبت نشده است.")
    }

    Text(
        "برنامه انتشار",
        style = MaterialTheme.typography.titleLarge
    )

    OutlinedTextField(
        value = videoName,
        onValueChange = { videoName = it },
        label = { Text("نام ویدیو") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = publishDate,
        onValueChange = { publishDate = it },
        label = { Text("تاریخ انتشار؛ مثال: 2026-11-01") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = publishTime,
        onValueChange = { publishTime = it },
        label = { Text("زمان انتشار؛ مثال: 18:00") },
        modifier = Modifier.fillMaxWidth()
    )

    Button(
        onClick = {
            if (
                videoName.isBlank() ||
                publishDate.isBlank() ||
                publishTime.isBlank()
            ) {
                status = "نام ویدیو، تاریخ و زمان را وارد کن."
            } else {
                scheduleList +=
                    "• ${videoName.trim()} | " +
                    "${publishDate.trim()} | " +
                    "${publishTime.trim()}\n"

                status =
                    "برنامه در فهرست محلی ثبت شد؛ " +
                    "انتشار خودکار فعال نیست."

                videoName = ""
                publishDate = ""
                publishTime = ""
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("افزودن به برنامه")
    }

    Text(status)

    if (scheduleList.isNotBlank()) {
        HorizontalDivider()

        Text(
            "فهرست برنامه‌ها",
            style = MaterialTheme.typography.titleMedium
        )

        Text(scheduleList)
    }

    Text(
        "این فهرست فعلاً داخل نشست برنامه نگهداری می‌شود؛ " +
            "برای ذخیره دائمی باید پایگاه داده اضافه شود.",
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun AnalyticsPage() {
    Text(
        "آمار و رشد کانال",
        style = MaterialTheme.typography.titleLarge
    )

    Text(
        "این بخش برای اتصال به آمار واقعی کانال آماده شده است."
    )

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("بازدیدها: هنوز متصل نشده")
            Text("زمان تماشا: هنوز متصل نشده")
            Text("مشترکان: هنوز متصل نشده")
            Text("عملکرد ویدیوها: هنوز متصل نشده")
        }
    }

    Text(
        "برای دریافت آمار واقعی باید ورود امن گوگل، " +
            "مجوزهای لازم و اتصال مناسب YouTube Analytics API " +
            "تنظیم شود."
    )

    Text(
        "تا زمان اتصال، این اعداد عمداً نمایش داده نمی‌شوند " +
            "تا اطلاعات ساختگی با آمار واقعی اشتباه نشود.",
        style = MaterialTheme.typography.bodySmall
    )
}