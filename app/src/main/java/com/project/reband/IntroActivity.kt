package com.project.reband

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.project.reband.viewmodel.IntroViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IntroActivity : AppCompatActivity() {

    private val viewModel : IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.loadedAllData.collect {
                if (it) {
                    val intent = Intent(this@IntroActivity, MainActivity::class.java)
                     startActivity(intent)
                     finish()
                }
            }
        }

        lifecycleScope.launch {
            listOf(
                async { viewModel.getLocationList() },
                async { viewModel.getInstrumentList() },
                async { viewModel.getHashTagList() }
            ).awaitAll()
            viewModel.finishLoadData()
        }

    }
}