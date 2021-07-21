package com.venuesApp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.venuesApp.R
import com.venuesApp.data.model.Venue

class VenuesAdapter(
    val venues: MutableList<Venue>,
    val openDetailsVenueListener: (Venue) -> Unit
) :
    RecyclerView.Adapter<VenuesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.venue, parent, false)
        return VenuesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun setVenues(_venues: List<Venue>) {
        this.venues.clear()
        this.venues.addAll(_venues)
        notifyDataSetChanged()
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class VenuesViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.title_venue)
        val tvLocation: TextView = itemView.findViewById(R.id.location_venue)
    }

    override fun getItemCount(): Int {
        return venues.size
    }
}