package com.esammahdi.yemekcalendar.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.esammahdi.yemekcalendar.R

val RegularFont = FontFamily(
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppinsmedium, FontWeight.Medium),
    Font(R.font.poppinssemibold, FontWeight.SemiBold),
    Font(R.font.poppinssemibold, FontWeight.Bold),
)

val SplashScreen = FontFamily(
    Font(R.font.michroma, FontWeight.Normal)
)

val LabelTextStyle = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = RegularFont,
)
