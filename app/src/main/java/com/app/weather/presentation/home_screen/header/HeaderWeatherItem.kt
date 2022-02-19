package com.app.weather.presentation.home_screen.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import  androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HeaderWeatherItem(
    calendar: Calendar,
    currentDate: Calendar,
    modifier: Modifier,
    onClick:  (calendar : Calendar)->Unit
){


    val dayOfWeekString = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH)



    val isSelected = currentDate.get(Calendar.DATE) == calendar.get(Calendar.DATE) && currentDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&  currentDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)

    var now = Calendar.getInstance()
    val isToday = now.get(Calendar.DATE) == calendar.get(Calendar.DATE) && now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&  now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
    val dayString = SimpleDateFormat("d/MM").format(calendar.time)
    val dayOfWeek = if(isToday) "Today" else dayOfWeekString
    val backgroundGradient = Modifier.background(brush = Brush.verticalGradient(
        colors = listOf(
            Color(44, 73, 129),
            Color(83,173,236),
        )
    ))
    val background = Modifier.background(color = Color(44, 73, 129))

    Box(modifier = modifier.then(if(isSelected) backgroundGradient else background).
        height(45.dp)
        .padding(top = 2.dp).then(
            if (!isSelected) Modifier.clickable(
                onClick = {
                    onClick(calendar)
                }
            )
            else Modifier
        )

    ) {
        Column( ) {
            Text(text = "${dayOfWeek}", color = Color.White, textAlign = TextAlign.Center, fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth())
            Text(text = "${dayString}", color = Color.White, textAlign = TextAlign.Center, fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth())
        }
    }
}