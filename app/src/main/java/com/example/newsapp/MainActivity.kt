package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.di.module.LocalHttpClient
import com.example.newsapp.system.notifications.NotificationService
import com.example.newsapp.ui.home.NavGraphs
import com.example.newsapp.ui.home.destinations.HomescreenDestination
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.utils.Utils
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.createNotificationChannel(
            this,
            "News",
            "Channel for News related notifications",
            NotificationService.CHANNEL_ID
        )
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            startRoute = HomescreenDestination
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        //Greeting("Android")
    }
}