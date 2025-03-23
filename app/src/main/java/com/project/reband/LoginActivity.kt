package com.project.reband

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.project.reband.ui.theme.RebandTheme
import com.project.reband.viewmodel.LoginActivityViewModel
import com.project.reband.viewmodel.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.userInfo.collectLatest {
                it?.let {
                    GlobalApplication.getInstance().getDataStore().apply {
                        setJwtToken(it.jwtToken)
                        setInstrument(it.memberInfo.instrument)
                        setNickName(it.memberInfo.nickName)
                        setExperience(it.memberInfo.experience)
                        setBandNo(it.bandInfo.bandNo)
                        setUserGrade(it.bandInfo.grade)
                        setBandName(it.bandInfo.bandName)
                    }
                    viewModel.loginSuccess()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loginResult.collectLatest { result ->
                when (result) {
                    is LoginResult.Success -> {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else -> {}
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            RebandTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }

    }

}


@Composable
fun LoginScreen(modifier: Modifier = Modifier, viewModel: LoginActivityViewModel) {
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
                        startKakaoLogin(viewModel)
                    }
            )
            Image(
                painter = painterResource(R.drawable.naver_login_btn_full),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(86.dp).padding(vertical = 4.dp)
                    .clickable {
                        startNaverLogin(viewModel)
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


private fun startKakaoLogin(vm: LoginActivityViewModel) {
    val context = LocalContext.current

    val userApiClient = UserApiClient.instance
    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("tngur", "1. 카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("tngur", "카카오계정으로 로그인 성공 ${token.accessToken}")
        }
    }

    // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
    if (userApiClient.isKakaoTalkLoginAvailable(context)) {
        userApiClient.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                userApiClient.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Log.i("tngur", "카카오톡으로 로그인 성공 ${token.accessToken} , ${token.refreshToken}")
                vm.getUserInfoKakao(token.accessToken, token.refreshToken)
            }
        }
    } else {
        userApiClient.loginWithKakaoAccount(context, callback = callback)
    }
}


private fun startNaverLogin(vm: LoginActivityViewModel) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult<Intent, ActivityResult>(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            RESULT_OK -> {
                // 네이버 로그인 인증이 성공했을 때 수행
                val accessToken = NaverIdLoginSDK.getAccessToken() ?: ""
                val refreshToken = NaverIdLoginSDK.getRefreshToken() ?: ""
                if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                    vm.getUserInfoNaver(accessToken, refreshToken)
                }
            }
            RESULT_CANCELED -> {
                Toast.makeText(context,"로그인 실패",Toast.LENGTH_LONG).show()
            }
        }
    }

    NaverIdLoginSDK.authenticate(context, launcher)
}




@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RebandTheme {
        LoginScreen(Modifier, viewModel = LoginActivityViewModel())
    }
}