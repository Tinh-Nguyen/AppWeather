package com.app.weather.presentation.home_screen.weather_detail

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    percentage: Float,
    id  : String,
    radius: Dp = 40.dp,
    color: Color = Color.White,
    strokeWidth : Dp = 5.dp,
    strokeWidthBackground : Dp = 3.dp,
    animationDuration: Int = 1000,
    animationDelay : Int = 0,
    isLoading: Boolean = false,
    content: @Composable() ()->Unit
){
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    var animationCompleted by remember {
        mutableStateOf(false)
    }

    val realPercentage = percentage * 0.7f
    LaunchedEffect(key1 = id){
        Log.d("CircularProgressBar","LaunchedEffect1111")
        animationPlayed = true
        animationCompleted = false
    }

    var  currentPercenttage = animateFloatAsState(

        targetValue = if(animationPlayed) realPercentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ),
       finishedListener = {
           animationCompleted = true
           animationPlayed = false
        }
    )


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(size = (radius * 2f))){
            drawArc(
                color = Color.White.copy(alpha = 0.6f),
                145f,
                360 * 0.7f,
                useCenter =  false,
                style = Stroke( strokeWidthBackground.toPx(), cap = StrokeCap.Round)
            )
        }
        val value = if(isLoading) 0f else if (animationCompleted)  realPercentage else currentPercenttage.value
        Canvas(modifier = Modifier.size(radius * 2f)){
            drawArc(
                color = color,
                145f,
                360 * value,
                useCenter =  false,
                style = Stroke( strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        content()
    }
}