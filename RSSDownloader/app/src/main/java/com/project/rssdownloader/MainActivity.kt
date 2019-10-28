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
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class FeedEntry {
    var artist: String = ""
    var name: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""
    override fun toString(): String {
        return """
              name = $name
              artist = $artist
              releaseDate = $releaseDate
              imageURL = $imageURL
           
         """.trimIndent()
    }
}

class MainActivity : AppCompatActivity() {
    private val feedSize = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val url: String = when (item.itemId) {
            R.id.menuFree -> FeedLinks.FreeApps
            R.id.menuMovies -> FeedLinks.Movies
            R.id.menuPaid -> FeedLinks.PaidApps
            R.id.menuSongs -> FeedLinks.Songs
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

        downloadURL(url)
        return true
    }

    private fun downloadURL(url: String) {
        downloadData = DownloadData(this, xmlListView)
        ParseApplications.applications.clear()
        downloadData?.execute(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)

    }

    companion object {
        private val TAG = "MainActivity"


    }
}
