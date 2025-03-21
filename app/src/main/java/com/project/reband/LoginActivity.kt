package com.project.reband

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.project.reband.ui.theme.RebandTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RebandTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startKakaoLogin() {
        val userApiClient = UserApiClient.instance
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("tngur", "1. 카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("tngur", "카카오계정으로 로그인 성공 ${token.accessToken}")
                // startMainActivity()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (userApiClient.isKakaoTalkLoginAvailable(this)) {
            userApiClient.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e("tngur", "2. 카카오톡으로 로그인 실패", error)


                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    userApiClient.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i("tngur", "카카오톡으로 로그인 성공 ${token.accessToken} , ${token.refreshToken}")


                }
            }
        } else {
            userApiClient.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun startNaverLogin() {
        val launcher = registerForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    // 네이버 로그인 인증이 성공했을 때 수행
                   // startMainActivity()
                }
                RESULT_CANCELED -> {
                    Toast.makeText(this,"로그인 실패",Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}



@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = Firebase.database
    val myRef = db.getReference("Users")

    Surface(
        color = colorResource(R.color.mainThemeColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                imageVector = Icons.Filled.MusicNote,
                contentDescription = null,
                modifier = Modifier.size(88.dp).padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(R.string.app_name),
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = Color.White,
                fontSize = 36.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Find your perfect bandmates now!",
                style = MaterialTheme.typography.labelSmall,
                color = colorResource(R.color.loginActivityTextColor),
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 40.dp)
            )
            Image(
                painter = painterResource(R.drawable.kakao_login_btn_full),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(80.dp).padding(vertical = 4.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                        myRef.setValue("kakao Login")
                    }
            )
            Image(
                painter = painterResource(R.drawable.naver_login_btn_full),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(86.dp).padding(vertical = 4.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
            )
            Text(
                text = "로그인 후 새로운 밴드 멤버를 모집해보세요",
                color = colorResource(R.color.loginActivityTextColor),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RebandTheme {
        LoginScreen(Modifier)
    }
}