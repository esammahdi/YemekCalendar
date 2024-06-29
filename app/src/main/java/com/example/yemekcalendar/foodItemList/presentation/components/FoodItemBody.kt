package com.example.yemekcalendar.foodItemList.presentation.screens.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.presentation.components.BackToTopButton
import com.example.yemekcalendar.foodItemList.presentation.InstitutionDropdown
import com.example.yemekcalendar.foodItemList.presentation.viewmodel.FoodDetailsViewModel
import com.example.yemekcalendar.foodItemList.presentation.viewmodel.FoodItemDetailsState
import kotlinx.coroutines.launch

@Composable
fun FoodItemBody(
    foodItemDetailsState: FoodItemDetailsState,
    foodItemsViewModel: FoodDetailsViewModel,
    minTitleOffset: Dp,
    imageOverlap: Dp,
    gradientScroll: Dp,
    titleHeight: Dp,
    hzPadding: Modifier,
    scroll: ScrollState
) {
    var seeMore by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    val showBackToTopButton by remember {
        derivedStateOf {
            scroll.value > 0
        }
    }

    val textButton = if (seeMore) {
        stringResource(id = R.string.see_more)
    } else {
        stringResource(id = R.string.see_less)
    }

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(minTitleOffset)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                Modifier.verticalScroll(scroll)
            ) {
                Spacer(Modifier.height(gradientScroll))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RectangleShape
                        )
                        .clip(RectangleShape),
                    shape = RectangleShape,
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column {
                        Spacer(Modifier.height(imageOverlap))
                        Spacer(Modifier.height(titleHeight))

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.detail_header),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = hzPadding
                        )

                        Spacer(Modifier.height(16.dp))


                        Text(
                            text = stringResource(R.string.detail_placeholder),
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                            overflow = TextOverflow.Ellipsis,
                            modifier = hzPadding
                        )


                        Text(
                            text = textButton,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .heightIn(20.dp)
                                .fillMaxWidth()
                                .padding(top = 15.dp)
                                .clickable {
                                    seeMore = !seeMore
                                }
                        )

                        Spacer(Modifier.height(24.dp))

                        HorizontalDivider(
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f),
                            thickness = 1.dp,
                        )

                        Spacer(Modifier.height(24.dp))

                        Text(
                            text = stringResource(R.string.days_served),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = hzPadding
                        )

                        Spacer(Modifier.height(16.dp))

                        InstitutionDropdown(
                            institutionList = foodItemDetailsState.institutionList,
                            selectedInstitution = foodItemDetailsState.selectedInstitution,
                            onInstitutionSelected = { foodItemsViewModel.onInstitutionSelected(it) }
                        )

                        Spacer(Modifier.height(16.dp))
                        FoodItemCalendar(daysServed = foodItemDetailsState.daysServed)
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }


            androidx.compose.animation.AnimatedVisibility(
                visible = showBackToTopButton,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                BackToTopButton {
                    scope.launch {
                        scroll.animateScrollTo(0)
                    }
                }
            }

        }
    }
}