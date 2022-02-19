package com.app.weather.presentation.home_screen.weather_detail

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.weather.R
import com.app.weather.domain.model.Weather
import com.app.weather.presentation.components.LoadingView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherDetail( viewModel: WeatherDetailViewModel = hiltViewModel(), modifier: Modifier = Modifier) {

    fun  getResourceIcon(iconName: String): Int{
        when(iconName){
            "sn"-> return R.drawable.sn
            "sl"-> return R.drawable.sl
            "h"-> return R.drawable.h
            "t"-> return R.drawable.t
            "hr"-> return R.drawable.hr
            "lr"-> return R.drawable.lr
            "s"-> return R.drawable.s
            "hc"-> return R.drawable.hc
            "lc"-> return R.drawable.lc
            "c"-> return R.drawable.c
        }
        return -1
    }

    val weather = viewModel.weatherState.value
    val weatherDetailState = viewModel.weatherDetailState.value


    var isLoading = false
    when(weatherDetailState){
        is WeatherDetailState.Success ->{

        }
        is WeatherDetailState.Loading ->{
            isLoading = true
        }
        is WeatherDetailState.Error ->{

        }

    }

   // val swipeRefreshState = rememberSwipeRefreshState(false)
//    val viewModel: WeatherDetailViewModel = viewModel()
//    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val viewModel: WeatherDetailViewModel = viewModel
   // var isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(viewModel.isRefreshing.value)
    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = { Log.d("onRefresh","onRefresh")
            viewModel.onEvent(WeatherEvent.Refresh(viewModel.currentDate.value))
                    },
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize(),) {

        }
        if(weather !== null || isLoading ){

            val currentDate = viewModel.currentDate.value
            val date = Calendar.getInstance()
            date.set( currentDate.get(Calendar.YEAR),  currentDate.get(Calendar.MONTH),  currentDate.get(Calendar.DATE))
            val dateString = SimpleDateFormat("E MMM dd, yyyy").format(date.time)


            Column( modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                Spacer(modifier = Modifier.weight(5f))

                if(!isLoading){
                    val icon = getResourceIcon(weather!!.weather_state_abbr)
                    Image(painter = painterResource(id = icon), contentDescription = "",
                        modifier = Modifier
                            .width(80.dp)
                            .weight(15f))
                }
                else{
                    LoadingView {
                        Box(modifier = Modifier
                            .weight(15f)
                            .align(Alignment.CenterHorizontally)){
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(it)
                            )
                        }
                    }
                }
                Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.weight(12f)) {
                    if(isLoading){
                        Text(modifier= Modifier , fontSize = 60.sp, text = "----", color = Color(44,73,129))
                    }else{
                        Text(modifier= Modifier , fontSize = 60.sp, text = weather!!.the_temp.toString()+"°", color = Color(44,73,129))
                        Text(modifier= Modifier.padding(vertical = 6.dp) , fontSize = 40.sp, text = "C", color = Color(44,73,129), textAlign = TextAlign.End)
                    }
                }
                if(isLoading){
                    Text(text = "----", color = Color(84, 175, 238), fontSize = 40.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(10f))
                }else{
                    Text(text = weather!!.weather_state_name, color = Color(84, 175, 238), fontSize = 35.sp,
                        modifier = Modifier.weight(10f))
                }

                Text(text = dateString, fontSize = 20.sp, color = Color.Black,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .weight(10f)
                )

                Row(modifier = Modifier
                    .padding(top = 10.dp)
                    .weight(40f)) {
                    WeatherPropetyDetail(id = weather?.id ?: "","Humidity", (weather?.humidity?:0) / 100f,
                        backgroundColor = Color(84, 174, 238), isLoading = isLoading
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    WeatherPropetyDetail(id = weather?.id ?: "","Predictability", ((weather?.predictability?:0)) / 100f,
                        backgroundColor = Color(81, 79, 211), isLoading = isLoading
                    )
                }
                Spacer(modifier = Modifier.weight(5f))
            }

        }else{
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text( textAlign = TextAlign.Center, text = "No data")
            }
        }
    }


}

@Composable
fun WeatherPropetyDetail(id: String, propertyName: String, percent: Float, backgroundColor :Color = Color(84, 174, 238), isLoading: Boolean = false,){
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(50))
            .background(color = backgroundColor)
    ){

        Column( modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(25.dp))
            CircularProgressBar(percentage = percent, id = id, isLoading = isLoading){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Row() {
                if(isLoading){
                    Text(text = "---" ,
                        fontSize = 20.sp, color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                }else{
                    Text(text = ((percent*100)).toInt().toString()+"" ,
                        fontSize = 20.sp, color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "%" , modifier = Modifier.padding(vertical = 6.dp),
                        fontSize = 15.sp, color = Color.White,
                    )
                }

            }
           
            Text(text = propertyName , fontSize = 13.sp, color = Color.White, textAlign = TextAlign.Center)
        }
    }
}