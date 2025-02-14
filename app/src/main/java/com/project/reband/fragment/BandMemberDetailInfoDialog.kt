package com.project.reband.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.reband.R
import com.project.reband.data.band.BandMemberData
import com.project.reband.databinding.BandMemberDetailInfoDialogBinding

class BandMemberDetailInfoDialog(
    private val data: BandMemberData
) : DialogFragment() {

    private val binding : BandMemberDetailInfoDialogBinding by lazy {
        BandMemberDetailInfoDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.apply {
            tvMemberName.text = data.memberNo.toString()

            val instrumentImage = when (data.instrumentNo) {
                1 -> R.drawable.guitar_ic_128
                2 -> R.drawable.drum_ic_128
                3 -> R.drawable.bass_ic_128
                4 -> R.drawable.vocal_ic_128
                5 -> R.drawable.keyboard_ic_128
                else -> R.drawable.guitar_ic_128
            }
            ivInstrument.setBackgroundResource(instrumentImage)


        }

        return binding.root
    }


}