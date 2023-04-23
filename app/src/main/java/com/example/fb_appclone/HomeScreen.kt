package com.example.fb_appclone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun HomeScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.app_name))
        }
    }
}