package com.project.reband


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.project.reband.network.etc.EtcRepository
import com.project.reband.ui.theme.RebandTheme
import com.project.reband.viewmodel.LoginActivityViewModel
import com.project.reband.viewmodel.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
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

    val context = LocalContext.current

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
                        viewModel.startKakaoLogin(context)
                    }
            )
            Image(
                painter = painterResource(R.drawable.naver_login_btn_full),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(86.dp).padding(vertical = 4.dp)
                    .clickable {
                        viewModel.startNaverLogin(context)
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
        LoginScreen(Modifier, viewModel = LoginActivityViewModel(etcRepository = EtcRepository()))
    }
}