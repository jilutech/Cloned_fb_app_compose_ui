package com.example.fb_appclone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fb_appclone.ui.theme.Fb_AppCloneTheme
import com.example.fb_appclone.ui.theme.PurpleGrey40
import com.google.android.material.tabs.TabItem

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn{
            item{
                TopBar()
            }
            item {
                TabBar()
            }
            item {
                statusUpdateBar("")
            }
        }

    }


}

@Composable
fun statusUpdateBar(
    avatar:String
) {
    Surface() {
        Row(Modifier.fillMaxWidth()) {

           AsyncImage(model = ImageRequest.Builder(
            LocalContext.current
           )
               .data(avatar)
               .crossfade(true)
               .placeholder(R.drawable.ic_launcher_background)
               .build(), contentDescription =null,
               modifier = Modifier.size(48.dp)
                                  .clip(CircleShape )

               )
            
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
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HomeScreen()
}

data class TabItem(
    val icon: ImageVector,
    val contentDescription: String,
)
