package com.los3molineros.laligasantander.ui.results

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.common.CommonFunctions.blink
import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import com.los3molineros.laligasantander.common.CommonFunctions.formatTo
import com.los3molineros.laligasantander.common.CommonFunctions.toDate
import com.los3molineros.laligasantander.data.model.MatchFirestore
import com.los3molineros.laligasantander.databinding.RoundResultItemBinding
import com.los3molineros.laligasantander.ui.matchDetail.MatchDetailActivity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ResultAdapter(private var matchList: List<MatchFirestore>?) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    fun setResults(matchList: List<MatchFirestore>?) {
        this.matchList = matchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.round_result_item, parent, false)

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) =
        holder.bind(matchList?.get(position))

    override fun getItemCount(): Int = matchList?.size ?: 0

    inner class ViewHolder(private val view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val binding = RoundResultItemBinding.bind(view)

        fun bind(match: MatchFirestore?) {
            binding.txtHomeTeam.text = match?.home_team?.short_code
            binding.txtAwayTeam.text = match?.away_team?.short_code
            Picasso.get().load(match?.home_team?.logo).into(binding.ivHomeLogo)
            Picasso.get().load(match?.away_team?.logo).into(binding.ivAwayLogo)
            binding.txtDate.text = match?.match_start?.subSequence(0, 10)

            if (match?.status_code == 3 || match?.status_code == 1 || match?.status_code == 11) {
                binding.txtResult.text = match.stats.ft_score
                binding.txtResult.setTextColor(Color.parseColor("#FFFFFFFF"))
            }

            if (match?.status_code == 17 || match?.status_code == 0 || match?.status_code == 4) {
                binding.txtResult.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.txtResult.text = match.match_start.subSequence(0,16).toString().toDate().formatTo("HH:mm")
            }

            if (match?.status_code == 1 || match?.status_code == 11) {
                binding.txtResult.setTextColor(Color.parseColor("#FF000000"))
                binding.txtResult.blink()
            }

            if (match?.status_code == 4) {
                binding.txtDate.text = view.context.getString(R.string.postponed)
            }

            if (match?.status_code == 1 ||  match?.status_code == 11 || match?.status_code == 12 || match?.status_code == 13) {
                binding.txtDate.text = view.context.getString(R.string.inplay)
            }

            if (match?.status_code == 3 ||  match?.status_code == 31 || match?.status_code == 32 ) {
                binding.txtDate.text = view.context.getString(R.string.ended)
            }

            if (match?.status_code == 3 && match.stats.ft_score == null) {
                binding.txtResult.text = "${match.stats.home_score.toString()}-${match.stats.away_score}"
            }


            binding.llResult.setOnClickListener {
                val intent = Intent(context, MatchDetailActivity::class.java)
                intent.putExtra("MATCH_ID", match?.match_id)
                context.startActivity(intent)
            }
        }
    }
}

