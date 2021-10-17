package com.los3molineros.laligasantander.ui.standings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.data.model.StandingsUI
import com.los3molineros.laligasantander.databinding.StandingsItemBinding
import com.squareup.picasso.Picasso

class StandingAdapter(private var standingList: List<StandingsUI>): RecyclerView.Adapter<StandingAdapter.ViewHolder>() {
    fun setStandings(standingList: List<StandingsUI>) {
        this.standingList = standingList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.standings_item, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StandingAdapter.ViewHolder, position: Int) = holder.bind(standingList[position])

    override fun getItemCount(): Int = standingList.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val binding = StandingsItemBinding.bind(view)

        fun bind(standing: StandingsUI) {
            binding.txtPosition.text = standing.standing.position.toString()
            binding.txtName.text = standing.team?.name
            Picasso.get().load(standing.team?.logo).into(binding.ivShield)
            binding.txtPoints.text = standing.standing.points.toString()
        }
    }
}