package com.example.newsapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.newsapp.domain.model.responses.Article
import com.example.newsapp.ui.custom_views.GenericStateIndicatorView
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.ResourceState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Destination(start = true)
@Composable
fun Homescreen(
    navigator: DestinationsNavigator
) {

    val viewmodel = koinViewModel<HomeViewmodel>()

    val state = viewmodel.news.collectAsState()

    val newsList = remember { mutableStateListOf<Article>() }

    var sort by remember { mutableStateOf(Constants.Sort.NONE) }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        viewmodel.onEvent(HomeEvents.FetchNews)
    })

    LaunchedEffect(key1 = state.value, block = {
        if (state.value.isSuccess()) {
            val data = (state.value as ResourceState.Success).data
            data.articles?.let {
                newsList.addAll(it)
            }
        }
    })

    LaunchedEffect(key1 = sort, block = {
        when (sort) {
            Constants.Sort.NONE -> {
                if (state.value.isSuccess()) {
                    newsList.clear()
                    val data = (state.value as ResourceState.Success).data
                    data.articles?.let {
                        newsList.addAll(it)
                    }
                }
            }

            Constants.Sort.ASCENDING -> {
                newsList.sortBy { article ->
                    article.publishedAt?.let { publishedAt ->
                        article.getFormattedDate()
                    }
                }
            }

            Constants.Sort.DESCENDING -> {
                newsList.sortByDescending { article ->
                    article.publishedAt?.let { publishedAt ->
                        article.getFormattedDate()
                    }
                }
            }
        }
    })

    Column {

        AppBar(modifier = Modifier.fillMaxWidth(), name = "News+", sort = {
            sort = when (sort) {
                Constants.Sort.NONE -> {
                    Toast.makeText(context, "Sorting by new to old", Toast.LENGTH_SHORT)
                        .show()

                    Constants.Sort.ASCENDING
                }

                Constants.Sort.ASCENDING -> {
                    Toast.makeText(context, "Sorting by old to new", Toast.LENGTH_SHORT)
                        .show()
                    Constants.Sort.DESCENDING
                }

                Constants.Sort.DESCENDING -> {
                    Toast.makeText(context, "Sorting by none (resetting list)", Toast.LENGTH_SHORT)
                        .show()
                    Constants.Sort.NONE
                }
            }
        })

        GenericStateIndicatorView(
            modifier = Modifier
                .fillMaxSize(),
            state = state.value,
            operationToPerformOnFailedState = {
                viewmodel.onEvent(HomeEvents.FetchNews)
            }) {

            NewsList(
                modifier = Modifier.fillMaxSize(),
                data = newsList,
                navigator = navigator
            )

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier,
    name: String,
    sort: () -> Unit
) = Box(modifier = modifier) {

    TopAppBar(
        title = {
            Text(text = name)
        }, colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            IconButton(onClick = { sort() }) {

                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.onPrimary
                )

            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}