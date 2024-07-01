package com.example.yemekcalendar.settings.presentation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.yemekcalendar.R
import com.example.yemekcalendar.core.other.navigation.Screen
import com.example.yemekcalendar.core.presentation.components.ShimmerEffect
import com.example.yemekcalendar.core.presentation.components.Title
import com.example.yemekcalendar.settings.presentation.components.ProfileShimmerLoadingScreen
import com.example.yemekcalendar.settings.presentation.viewmodel.ProfileViewModel
import com.example.yemekcalendar.ui.theme.LabelTextStyle
import com.example.yemekcalendar.ui.theme.RegularFont
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    profileViewModel.initialize()

    val timeZone = ZoneId.systemDefault().id

    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var imageBiggetThanLimit = false
    val imageUri = profileState.profilePictureDownloadUrl

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = imageUri)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                error(R.drawable.profile_picture_placeholder)
                transformations(CircleCropTransformation())
            })
            .build()
    )

    val imageState = painter.state

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(it)
            val fileSizeInBytes = inputStream?.available() ?: 0
            val fileSizeInMB = fileSizeInBytes / (1024 * 1024)
            inputStream?.close()

            if (fileSizeInMB <= 5) {
                profileViewModel.changeProfilePicture(it)
            } else {
                imageBiggetThanLimit = true
            }
        }
    }

    if (imageBiggetThanLimit) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                "Image size must be less than 5 MB",
                Toast.LENGTH_LONG
            ).show()
            imageBiggetThanLimit = false
        }
    }

    if (profileState.isProfilePictureChangeError) {
        LaunchedEffect(Unit) {
            Toast.makeText(
                context,
                profileState.profilePictureChangeErrorMessage,
                Toast.LENGTH_LONG
            ).show()
        }
        profileViewModel.resetProfilePictureChangeState()
    }

    if (profileState.isLoggoedOut) {
        AlertDialog(
            onDismissRequest = {
                navController.navigate(Screen.LoginScreen)
            },
            title = { Text(text = stringResource(R.string.logout_dialog_title)) },
            text = { Text(text = stringResource(R.string.logout_dialog_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        navController.navigate(Screen.LoginScreen)
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }

    when {
        profileState.isLoading -> {
            ProfileShimmerLoadingScreen()
        }

        else -> {
            Scaffold(
                modifier = Modifier.padding(top = 0.dp),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.profile),
                                style = LabelTextStyle
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigateUp()
                            }) {
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
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {

                    Title(text = stringResource(R.string.welcome_back_message))

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape,
                            modifier = Modifier.size(250.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(
                                        width = 2.dp, // Adjust the width of the border as needed
                                        color = MaterialTheme.colorScheme.primary, // Adjust the color of the border as needed
                                        shape = CircleShape
                                    )
                            ) {
                                if (imageState is AsyncImagePainter.State.Loading) {
                                    ShimmerEffect(modifier = Modifier.fillMaxSize())
                                }
                                Image(
                                    painter = painter,
                                    contentDescription = "Profile Image",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.upload_profile_picture_message),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = {
                            launcher.launch(
                                input = "image/*",
                                options = null
                            )
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_upload_24),
                            contentDescription = "Decorative Element. An upload icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = stringResource(R.string.upload),
                        )
                    }


                    // User info
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(16.dp))

                        InfoRow(
                            label = stringResource(R.string.email),
                            value = profileState.email
                        )
                        InfoRow(
                            label = stringResource(R.string.contact),
                            value = stringResource(R.string.not_applicable)
                        )
                        InfoRow(
                            label = stringResource(R.string.time_zone),
                            value = timeZone
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons
                    Button(
                        shape = MaterialTheme.shapes.small,
                        onClick = { profileViewModel.onLogoutButtonClicked() },
                    ) {
                        Text(
                            text = stringResource(R.string.logout)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = LabelTextStyle
        )

        Text(
            text = value,
            style = ContentTextStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MockProfileScreen(navController: NavController) {

    val imageUrl = "https://via.placeholder.com/150"

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                error(R.drawable.food_image_placeholder)
                transformations(CircleCropTransformation())
            }).build()
    )

    val imageState = painter.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar with back arrow and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Profile", style = MaterialTheme.typography.titleLarge)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                contentColor = MaterialTheme.colorScheme.onBackground,
                shape = CircleShape,
                modifier = Modifier
                    .size(250.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (imageState is AsyncImagePainter.State.Loading) {
                        ShimmerEffect(modifier = Modifier.fillMaxSize())
                    }
                    Image(
                        painter = painter,
                        contentDescription = "Profile Image",
                        //                    placeholder = painterResource(R.drawable.food_image_placeholder),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "User settings",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Customizing",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }

        // User info
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow(label = "User", value = "Profile details")
            InfoRow(label = "Contact email", value = "Email address")
            InfoRow(label = "Contact", value = "Not applicable")
            InfoRow(label = "Time zone", value = "Current time display")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                elevation = ButtonDefaults.elevatedButtonElevation(),
                onClick = { }
            ) {
                Text(text = "Edit")
            }
            OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Video chat")
            }
            OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Logout")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_8_pro")
@Composable
fun ProfileInfoPagePreview() {
    MockProfileScreen(navController = rememberNavController())
}



