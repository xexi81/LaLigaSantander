package com.los3molineros.laligasantander.ui.matchDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.laligasantander.R
import com.los3molineros.laligasantander.common.CommonFunctions.debugLog
import com.los3molineros.laligasantander.data.model.MatchEvents
import com.los3molineros.laligasantander.databinding.EventHomeItemBinding

class EventsAdapter(private var matchEventsList: List<MatchEvents>, private val homeTeam: Int, private val awayTeam: Int): RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    fun setEvents(matchEventsList: List<MatchEvents>) {
        this.matchEventsList = matchEventsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.ViewHolder {
        when (viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.event_home_item, parent, false)
                return ViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.event_away_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: EventsAdapter.ViewHolder, position: Int) = holder.bind(matchEventsList[position])

    override fun getItemCount(): Int = matchEventsList.size

    override fun getItemViewType(position: Int): Int {
        return if (matchEventsList[position].team_id == homeTeam) {
            1
        } else 2
    }


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val binding = EventHomeItemBinding.bind(view)

        fun bind(matchEvents: MatchEvents) {

            binding.txtMinute.text = "${matchEvents.minute.toString()}'"
            binding.txtPlayer.text = matchEvents.player_name

            if (matchEvents.type == "substitution") {
                binding.txtPlayer.text = "${matchEvents.player_name} -> ${matchEvents.related_player_name}"
            }

            when(matchEvents.type) {
                "yellowcard" -> { binding.ivType.setImageResource(R.drawable.yellow_card)}
                "goal" -> { binding.ivType.setImageResource(R.drawable.goal)}
                "substitution" -> { binding.ivType.setImageResource(R.drawable.substitution)}
                "injury" -> { binding.ivType.setImageResource(R.drawable.injury)}
                "redcard" -> { binding.ivType.setImageResource(R.drawable.red_card)}
                "yellowredcard" -> { binding.ivType.setImageResource(R.drawable.red_card)}
            }
        }
    }

}













