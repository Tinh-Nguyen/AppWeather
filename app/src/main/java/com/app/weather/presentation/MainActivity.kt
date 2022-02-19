package com.app.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.app.weather.presentation.home_screen.HomeScreen
import com.app.weather.presentation.home_screen.header.Header
import com.app.weather.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
        setContent {
            AppTheme{
                HomeScreen()
            }

        }
    }
}