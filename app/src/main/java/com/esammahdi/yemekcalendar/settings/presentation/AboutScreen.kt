package com.esammahdi.yemekcalendar.settings.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.esammahdi.yemekcalendar.R
import com.esammahdi.yemekcalendar.core.presentation.components.Title
import com.esammahdi.yemekcalendar.ui.theme.RegularFont

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AboutScreen(navController: NavController) {
    val context = LocalContext.current

    // CustomTypography.kt or within the same file
    val LabelTextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = RegularFont,
        color = MaterialTheme.colorScheme.onBackground
    )

    val ContentTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = RegularFont,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onBackground
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.about),
                        style = LabelTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                text = stringResource(R.string.app_name),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = stringResource(R.string.description),
                style = ContentTextStyle.copy(
                    textAlign = TextAlign.Justify,
                    fontStyle = FontStyle.Normal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description section
            DescriptionRow(label = stringResource(R.string.author), content = "Esameldin Bashir")
            DescriptionRow(label = stringResource(R.string.year), content = "2024")
            DescriptionRow(label = stringResource(R.string.license), content = "MIT License")
            DescriptionRow(label = stringResource(R.string.version), content = "1.0")

            Spacer(modifier = Modifier.height(16.dp))

            // Technologies section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.technologies),
                    style = LabelTextStyle,
                    modifier = Modifier.weight(2f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                FlowRow(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TechnologyLogo(
                        painter = painterResource(R.drawable.firebase),
                        contentDescription = "Firebase"
                    )
                    TechnologyLogo(
                        painter = painterResource(R.drawable.room),
                        contentDescription = "Room"
                    )
                    TechnologyLogo(
                        painter = painterResource(R.drawable.mvvm),
                        contentDescription = "MVVM"
                    )
                    TechnologyLogo(
                        painter = painterResource(R.drawable.dagger_hilt),
                        contentDescription = "Dagger Hilt"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // GitHub link section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://github.com/esammahdi/YemekCalendar")
                        )
                        context.startActivity(intent)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_github),
                    contentDescription = "GitHub",
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "View on GitHub",
                    color = MaterialTheme.colorScheme.primary,
                    style = ContentTextStyle.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}

@Composable
fun DescriptionRow(label: String, content: String) {
    val LabelTextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontFamily = RegularFont,
        color = MaterialTheme.colorScheme.onBackground
    )

    val ContentTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = RegularFont,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onBackground
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = LabelTextStyle,
            modifier = Modifier.weight(2f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = content,
            style = ContentTextStyle,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun TechnologyLogo(painter: Painter, contentDescription: String) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(48.dp)
            .padding(end = 8.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(navController = rememberNavController())
}





