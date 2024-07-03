package com.esammahdi.yemekcalendar.foodItemList.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.presentation.components.ShimmerEffect


@Composable
fun FoodItemImage(
    imageUrl: String?,
    itemName: String,
    minTitleOffset: Dp,
    maxTitleOffset: Dp,
    hzPadding: Modifier,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (maxTitleOffset - minTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                error(R.drawable.food_image_placeholder)
                transformations(CircleCropTransformation())
            }).build()
    )

    val imageState = painter.state

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = hzPadding.statusBarsPadding(),
        minTitleOffset = minTitleOffset,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            contentColor = MaterialTheme.colorScheme.onBackground,
            shape = CircleShape,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (imageState is AsyncImagePainter.State.Loading) {
                    ShimmerEffect(modifier = Modifier.fillMaxSize())
                }
                Image(
                    painter = painter,
                    contentDescription = itemName,
//                    placeholder = painterResource(R.drawable.food_image_placeholder),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}


//@Composable
//fun ShimmerEffect(
//    modifier: Modifier = Modifier
//) {
//    val gradient = listOf(
//        Color.LightGray.copy(alpha = 0.6f),
//        Color.LightGray.copy(alpha = 0.3f),
//        Color.LightGray.copy(alpha = 0.6f)
//    )
//
//    val infiniteTransition = rememberInfiniteTransition()
//    val xShimmer by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1000f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = 1000,
//                easing = LinearEasing
//            ),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//
//    val yShimmer by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1000f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = 1000,
//                easing = LinearEasing
//            ),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//
//    val brush = remember {
//        Brush.linearGradient(
//            colors = gradient,
//            start = Offset(xShimmer, yShimmer),
//            end = Offset(xShimmer + 200f, yShimmer + 200f),
//            tileMode = TileMode.Mirror
//        )
//    }
//
//    Canvas(modifier = modifier) {
//        drawRect(brush = brush)
//    }
//}
