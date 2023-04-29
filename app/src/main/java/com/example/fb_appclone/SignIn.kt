package com.example.fb_appclone

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignIn(
    navigateHome : () -> Unit
){
    val context = LocalContext.current
Box(modifier = Modifier
    .background(Color.White)
    .fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(Icons.Rounded.Face, contentDescription =null , modifier = Modifier.size(90.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.sign_with))
        signInButton(
            onSignInFailed = {
           Toast.makeText(context,"welcome",Toast.LENGTH_LONG)
                navigateHome()
        },
            onSignedIn = {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG)

        }

        )
    }
  }
}

@Composable
fun signInButton(
    onSignInFailed : (FacebookException) -> Unit,
    onSignedIn : () -> Unit
) {
    var scope = rememberCoroutineScope()
    AndroidView({context ->
        LoginButton(context).apply{
            val callbackManager = CallbackManager.Factory.create()
            setPermissions("email","public_profile")
            registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
                override fun onCancel() {
//                    do nothing
                }

                override fun onError(error: FacebookException) {
                    onSignInFailed(error)
                }

                override fun onSuccess(result: LoginResult) {
                    scope.launch {
                        val token = result.accessToken.token
                        val credential = FacebookAuthProvider.getCredential(token)
                        val authResult = Firebase.auth.signInWithCredential(credential).await()
                        if (authResult.user != null){
                            onSignedIn()
                        }else{
                            onSignInFailed(RuntimeException("Cold not sign in") as FacebookException)
                        }
                    }

                }

            })
        }
    } )
}
