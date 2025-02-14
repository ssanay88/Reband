package com.project.reband.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.project.reband.databinding.FragmentBandRegisterBinding
import com.project.reband.viewmodel.BandRegisterViewModel

class BandRegisterFragment : Fragment() {

    private val binding: FragmentBandRegisterBinding by lazy {
        FragmentBandRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: BandRegisterViewModel by viewModels()

    private var bandName: String = ""
    private var bandImage: Uri? = null
    private var locationFirstDepth: String = ""
    private var locationSecondDepth: String = ""
    private var introduce: String = ""
    private var mediaUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // openGallery()
            }
        }

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    bandImage = it
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            btnCancel.setOnClickListener {
                back()
            }

            btnRegister.setOnClickListener {
                createBand()
            }

            btnBandThumbnail.setOnClickListener {
                getBandImage()
            }
        }

        return binding.root
    }

    private fun back() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun createBand() {

    }

    private fun getBandImage() {
        // val targetPermisstion = Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

}