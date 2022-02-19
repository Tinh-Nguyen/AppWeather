package com.app.weather.presentation.home_screen.header

import java.util.*

sealed class HeaderEvent {
    data class SelectDate(val date: Calendar) : HeaderEvent()
}