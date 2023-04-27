package com.example.fb_appclone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.login.widget.LoginButton

@Composable
fun SignIn(){
Box(modifier = Modifier
    .background(Color.White)
    .fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(Icons.Rounded.Face, contentDescription =null , modifier = Modifier.size(90.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.sign_with))
        signInButton()
    }
  }
}

@Composable
fun signInButton() {
    AndroidView({context ->
        LoginButton(context)
    } )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SignIn()
}