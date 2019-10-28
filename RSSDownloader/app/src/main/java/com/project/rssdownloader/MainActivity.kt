/*
 *
 *  Created by Darthcow
 *  Last modified 25/10/19 12:22
 * /
 */



package com.project.rssdownloader

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    var artist: String = ""
    var name: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

}

class MainActivity : AppCompatActivity() {
    private val feedSize = 10
    private val feedViewModel by lazy { ViewModelProviders.of(this).get(FeedViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val feedAdapter = FeedAdapter(this, R.layout.list_record, EMPTY_FEED_LIST)
        xmlListView.adapter = feedAdapter
        feedViewModel.feedEntries.observe(
            this,
            Observer<List<FeedEntry>> { feedEntries -> feedAdapter.setFeedList(feedEntries) })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val url: String = when (item.itemId) {
            R.id.menuFree -> FeedLinks.FreeApps
            R.id.menuMovies -> FeedLinks.Movies
            R.id.menuPaid -> FeedLinks.PaidApps
            R.id.menuSongs -> FeedLinks.Songs
            else -> {
                feedViewModel.invalidate()
                return super.onOptionsItemSelected(item)
            }
        }
        feedViewModel.downloadURL(url)
        return true
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        return true
    }
}
