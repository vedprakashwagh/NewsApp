package com.example.newsapp.ui.home

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import com.example.newsapp.data.local.Dummy
import com.example.newsapp.domain.model.responses.Article
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun NewsList(
    modifier: Modifier, data: List<Article>, navigator: DestinationsNavigator
) = Box(modifier = modifier) {
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            NotificationPermission(rgbColor = Color.Black, bodyTextColor = Color.White)
        }

        items(data) { article ->

            NewsArticle(
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                    .fillMaxWidth(), article = article,
                articleClicked = {
                    val intent = CustomTabsIntent.Builder().build()
                    intent.launchUrl(context, Uri.parse(article.url))
                }
            )

        }


    }
}


/**
 * Currently shouldShowDescription controls the state of visibility of description block.
 * Unfortunately since this state is never saved, it's reset after the list item is recycled.
 * To fix this, we'll need to create a new Model class containing state data of News Article as well.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticle(
    modifier: Modifier, article: Article, articleClicked: () -> Unit
) = Column(modifier = modifier) {

    val containerColor = remember { mutableStateOf(Color(0xff000000)) }
    val contentColor = remember { mutableStateOf(Color(0xffffffff)) }

    var shouldShowDescription by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        shape = ShapeDefaults.Medium,
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor.value,
            contentColor = contentColor.value
        ),
        border = BorderStroke(1.dp, contentColor.value),
        onClick = {
            articleClicked()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            CoilImage(imageModel = {
                article.urlToImage
            },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                component = rememberImageComponent {

                    +PalettePlugin {
                        val swatch = it.dominantSwatch ?: it.mutedSwatch ?: it.vibrantSwatch
                        ?: it.lightMutedSwatch ?: it.lightVibrantSwatch

                        containerColor.value = Color(swatch?.rgb ?: 0)
                        contentColor.value = Color(swatch?.bodyTextColor ?: 0)
                    }
                })

            Column(
                modifier = Modifier
                    .padding(12.dp)
                /*.weight(if (article.urlToImage?.isEmpty() == false) 0.65f else 1f)*/,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    article.source?.name ?: "Unavailable",
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor.value
                )

                Text(
                    text = article.title ?: "Unavailable",
                    style = MaterialTheme.typography.titleLarge,
                    color = contentColor.value
                )

                AnimatedVisibility(visible = shouldShowDescription) {
                    Text(
                        text = article.description ?: "Unavailable",
                        style = MaterialTheme.typography.bodySmall,
                        color = contentColor.value,
                        modifier = Modifier.padding(
                            top = 12.dp,
                            bottom = 12.dp
                        )
                    )
                }

                Text(
                    text = "${if (article.author != null) article.author + " • " else ""}${article.getDisplayDate()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.value
                )

                AnimatedVisibility(visible = !shouldShowDescription) {
                    ElevatedButton(
                        onClick = { shouldShowDescription = true },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = contentColor.value,
                            contentColor = containerColor.value
                        )
                    ) {
                        Text(text = "Quick read")
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xffffffff
)
@Composable
fun NewsArticlePreview() {

    NewsArticle(modifier = Modifier.fillMaxWidth(), article = Dummy.article, articleClicked = {

    })

}