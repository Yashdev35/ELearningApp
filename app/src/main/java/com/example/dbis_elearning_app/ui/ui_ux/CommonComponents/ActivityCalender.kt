package com.example.dbis_elearning_app.ui.ui_ux.CommonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class DayActivity(val day: Int, val level: Int) // level: 0, 1, 2 for low, medium, high

data class MonthActivity(val name: String, val days: List<DayActivity>)

@Composable
fun ActivityCalendar(modifier: Modifier = Modifier) {
    val months = listOf(
        MonthActivity("January", List(31) { DayActivity(it + 1, (0..2).random()) }),
        MonthActivity("February", List(28) { DayActivity(it + 1, (0..2).random()) }),
        // Add other months here...
    )

    var currentMonthIndex by remember { mutableStateOf(0) }
    val currentMonth = months[currentMonthIndex]

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val calendarHeight = screenHeight * 0.35f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(calendarHeight)
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
    ) {
        // Month name and navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (currentMonthIndex > 0) currentMonthIndex--
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous month", tint = Color.White)
            }
            Text(currentMonth.name, color = Color.White, fontWeight = FontWeight.Bold)
            IconButton(onClick = {
                if (currentMonthIndex < months.size - 1) currentMonthIndex++
            }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next month", tint = Color.White)
            }
        }

        // Calendar grid
        val daysInWeek = 7
        val weeks = currentMonth.days.chunked(daysInWeek)
        Column(modifier = Modifier.padding(start = 6.dp, bottom = 6.dp).fillMaxSize()) {
            weeks.forEach { week ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    week.forEach { day ->
                        DayCell(day)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // Fill empty spaces if the week is not complete
                    repeat(daysInWeek - week.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun DayCell(day: DayActivity) {
    val color = when (day.level) {
        1 -> Color(0xFF00FF04).copy(alpha = 0.2f)
        2 -> Color(0xFF00FF04).copy(alpha = 0.6f)
        3 -> Color(0xFF00FF04)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(46.dp)
            .padding(start = 8.dp, bottom = 2.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(6.dp))
            .background(color)
            .border(0.5.dp, Color(0xFF808080), RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.day.toString(),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun ActivityCalendarPreview() {
    ActivityCalendar()
}