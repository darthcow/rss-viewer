/*
 *
 *  Created by Darthcow
 *  Last modified 25/10/19 12:22
 * /
 */

package com.project.rssdownloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ViewHolder(view: View) {
    val tvName: TextView = view.findViewById(R.id.tvName)
    val tvArtists: TextView = view.findViewById(R.id.tvArtist)
    val tvSummary: TextView = view.findViewById(R.id.tvSummary)


}

class FeedAdapter(
    context: Context,
    private val resource: Int,
    private var applications: List<FeedEntry>
) :
    ArrayAdapter<FeedEntry>(context, resource) {


    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = applications.size



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val currentApp = applications[position]
        holder.tvName.text = currentApp.name
        holder.tvArtists.text = currentApp.artist
//        holder.tvSummary.text = currentApp.summary
//        this.notifyDataSetChanged()
        return view
    }

    fun setFeedList(feedEntries: List<FeedEntry>) {
    applications = feedEntries
        notifyDataSetChanged()
    }
}
