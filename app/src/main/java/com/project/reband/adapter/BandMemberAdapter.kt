package com.project.reband.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.reband.R
import com.project.reband.data.band.BandMemberData
import com.project.reband.databinding.VhBandMemberBinding
import com.project.reband.fragment.ConfirmDialog
import com.project.reband.fragment.ErrorDialog
import com.project.reband.viewmodel.MyBandInfoFragmentViewModel

class BandMemberAdapter(
    private val vm: MyBandInfoFragmentViewModel,
    private val userGrade: String,
    private val bandMemberList: List<BandMemberData>
) : RecyclerView.Adapter<BandMemberAdapter.BandMemberVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandMemberVH {
        val binding = VhBandMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BandMemberVH(binding)
    }

    override fun getItemCount(): Int {
        return bandMemberList.size
    }

    override fun onBindViewHolder(holder: BandMemberVH, position: Int) {
        holder.bind(bandMemberList[position])
    }

    inner class BandMemberVH(private val binding: VhBandMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: BandMemberData) {
            binding.apply {
                tvMemberInstrument.text = data.instrumentNo.toString()
                // tvMemberCareer.text = data.  년차 필요
                // tvMemberName.text = data.  멤버 이름 필요
                //
                if (data.memberGrade == "LEADER") {
                    tvMemberName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.crown_img, 0, 0, 0)
                } else {
                    tvMemberName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }

                tvMemberDeport.visibility = if (userGrade == "LEADER") View.VISIBLE else View.GONE

                tvMemberDetailInfo.setOnClickListener {
                    vm.showMemberInfoDialog(data)
                }

                tvMemberDeport.setOnClickListener {
                    vm.showDeportDialog(data.bandMemberNo.toString(), data.bandMemberNo, data.memberNo)

                }

            }
        }

    }
}
