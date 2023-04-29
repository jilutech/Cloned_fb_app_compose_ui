package com.example.fb_appclone

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fb_appclone.ui.theme.Fb_AppCloneTheme
import com.example.fb_appclone.ui.theme.PurpleGrey40
import com.google.android.material.tabs.TabItem
import java.util.*

@Composable
fun HomeScreen(
    navigateToSignIn : () -> Unit,
) {
    val viewModel = viewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()
    when (state) {
        is HomeScreenState.Loaded -> {
            val loaded = state as HomeScreenState.Loaded
            HomeScreenContent(posts = loaded.posts, avatarUrl = loaded.avatarUrl, onTextChanged = {
                viewModel.onTextChanged(it)
            }, onSendClick = {
                viewModel.onSendClick()
            })
        }
        HomeScreenState.Loading -> LoadingScreen()
        HomeScreenState.SignInRequired -> LaunchedEffect(Unit) {
            navigateToSignIn()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue), contentAlignment = Alignment.Center){
        CircularProgressIndicator()

    }
}

@Composable
private fun HomeScreenContent(
    posts: List<Post>,
    avatarUrl: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                TopBar()
            }
            item {
                TabBar()
            }
            item {
                statusUpdateBar("",
                    onTextChange = {

                    },
                    onSendClick = {

                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
//                PostCards()
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                storySection()
            }
        }
    }
}
@Composable
fun storySection() {
    Surface() {
        LazyRow(Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 8.dp
            , vertical = 8.dp))
        {
            item {
                CreateAStoryCard()
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAStoryCard() {
    Card(Modifier.size(140.dp,220.dp)) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
            val bgHeight by remember {
                mutableStateOf(0.dp)
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .height(bgHeight - 19.dp)
                    .align(Alignment.BottomCenter)
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .clip(CircleShape)
                    .size(36.dp),
                   contentAlignment = Alignment.Center
                    ) {
                     Icon(Icons.Rounded.Add,
                         contentDescription = stringResource(R.string.add),
                          tint = MaterialTheme.colorScheme.primary
                     )
                     }
                Spacer(modifier = Modifier.height(25.dp))
                Text(text = stringResource(R.string.create_a_story))
            }
        }

    }
}

@Composable
fun PostCards(post : Post) {
    Surface() {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically){
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data("url").crossfade(true).build(), contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape))
            Column(
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)) {
                
                Text(text = "aMy", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)

                val today = remember {
                    Date()
                }
                Text(dateLabel(timestamp = post.timestamp, today = today),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f))
            }
            IconButton(onClick = { }) {
                Icon(Icons.Rounded.MailOutline, contentDescription = stringResource(R.string.share) )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun statusUpdateBar(
    avatar:String,
    onTextChange:(String) -> Unit,
    onSendClick:() -> Unit
) {
    Surface() {
        Column{
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatar)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_launcher_background)
                        .build(),
                    contentDescription =null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))
//           Text(text = stringResource(id = R.string.whats_on_your_mind))
                var inputStatus by remember {
                    mutableStateOf("")
                }
                TextField(colors = TextFieldDefaults.textFieldColors(Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                    modifier = Modifier.fillMaxWidth(),
                    value = inputStatus,
                    onValueChange = {
                        inputStatus=it
                        onTextChange(it)
                    },
                    placeholder = {
                        Text(stringResource(R.string.whats_on_your_mind))
                    },
                    keyboardActions = KeyboardActions(onSend = {

                        onSendClick()
                        inputStatus=""
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send))

            }
            Divider(thickness = Dp.Hairline)
            Row {
                verticalDivider(modifier = Modifier.height(48.dp))
                StatusAction(Icons.Rounded.Home,
                    stringResource(R.string.live),
                    modifier = Modifier.weight(1f))
                verticalDivider(modifier = Modifier.height(48.dp))
                StatusAction(Icons.Rounded.Home,
                    stringResource(R.string.live),
                    modifier = Modifier.weight(1f))
                verticalDivider(modifier = Modifier.height(48.dp))
                StatusAction(Icons.Rounded.Home,
                    stringResource(R.string.live),
                    modifier = Modifier.weight(1f))
            }
        }

    }
}
@Composable
fun verticalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    thickness: Dp =1.dp,
    topIndent: Dp = 0.dp
) {
    val indentMod = if (topIndent.value != 0f) {
        Modifier.padding(top = topIndent)
    } else {
        Modifier
    }
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .then(indentMod)
            .fillMaxHeight()
            .width(targetThickness)
            .background(color = color)
    )
}

@Composable
private fun StatusAction(imageIcon : ImageVector,text : String,modifier: Modifier = Modifier) {

    TextButton(
        modifier = modifier,
        onClick = { },
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageIcon, contentDescription = text)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.live))
        }
    }
}

@Composable
private fun TopBar() {
    Surface() {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.weight(1f))
            // icon
            IconButton(
                onClick = { },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PurpleGrey40)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = stringResource(R.string.search))
            }
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = { },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(PurpleGrey40)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = stringResource(R.string.search))
            }
        }
    }

}

@Composable
private fun TabBar(){
    Surface() {
        var tabIndex by remember {
            mutableStateOf(0)
        }
        TabRow(
            selectedTabIndex = tabIndex,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            val tabs = listOf(
                TabItem(Icons.Rounded.Home, stringResource(R.string.home)),
                TabItem(Icons.Rounded.Home, stringResource(R.string.reels)),
                TabItem(Icons.Rounded.Home, stringResource(R.string.marketplace)),
                TabItem(Icons.Rounded.Home, stringResource(R.string.news)),
                TabItem(Icons.Rounded.Notifications, stringResource(R.string.notifications)),
                TabItem(Icons.Rounded.Menu, stringResource(R.string.menu)),
            )
            tabs.forEachIndexed { i, item ->
                Tab(selected = tabIndex == i,
                    onClick = { tabIndex = i },
                    modifier = Modifier.heightIn(48.dp)) {
                    Icon(item.icon,
                        contentDescription = item.contentDescription,
                        tint = if (i == tabIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.44f)
                        })
                }
            }



//            Tab(selected = tabIndex == 0, onClick = { tabIndex == 0},
//             selectedContentColor = MaterialTheme.colorScheme.primary,
//             unselectedContentColor = MaterialTheme.colorScheme.onSurface
//                ) {
//                Icon(Icons.Rounded.Home, contentDescription = stringResource(R.string.home) )
//            }
//            Tab(selected = tabIndex == 1, onClick = { tabIndex == 1},
//                selectedContentColor = MaterialTheme.colorScheme.primary,
//                unselectedContentColor = MaterialTheme.colorScheme.onSurface) {
//                Icon(Icons.Rounded.Home, contentDescription = stringResource(R.string.home) )
//            }
        }
    }

}

@Composable
private fun dateLabel(timestamp: Date, today: Date): String {
    return if (today.time - timestamp.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        stringResource(R.string.just_now)
    } else {
        DateUtils.getRelativeTimeSpanString(timestamp.time,
            today.time,
            0,
            DateUtils.FORMAT_SHOW_WEEKDAY).toString()
    }
}

//@Composable
//fun PostCard(post: Post){
//    Surface() {
//      Row(
//          Modifier
//              .fillMaxWidth()
//              .padding(8.dp)) {
//
//          AsyncImage(model = ImageRequest.Builder(LocalContext.current)
//              .data(post.authorAvatarUrl)
//              .crossfade(true)
//              .placeholder(R.drawable.baseline_person_24).build()
//              , contentDescription = null, modifier = Modifier.size(48.dp).clip(CircleShape)
//              )
//      }
//
//
//    }
//}
@Preview(showBackground = true)
@Composable
fun Preview(){
    CreateAStoryCard()
}

data class TabItem(
    val icon: ImageVector,
    val contentDescription: String,
)
