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
//        summary = $summary
    }
}

class MainActivity : AppCompatActivity() {
    //    private lateinit var downloadData: DownloadData
    private var downloadData: DownloadData? = null
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

        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {
            var mContext: Context by Delegates.notNull()
            var mListView: ListView by Delegates.notNull()

            init {
                mContext = context
                mListView = listView
            }

            override fun doInBackground(vararg p0: String?): String {
                Log.d(TAG, "doInBackground called starts with ${p0[0]}")
                val feed = downloadXML(p0[0])
                if (feed.isEmpty()) {
                    Log.d(TAG, "error downloading feed")
                }
                return feed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                ParseApplications.parse(result)
                Log.d(TAG, "onPostExecute called, parameter is $result ")
                //context,item format, list of itens
//                val arrayAdapter = ArrayAdapter<FeedEntry>(
//                    mContext,
//                    R.layout.list_item,
//                    ParseApplications.applications
//                )

//                mListView.adapter = arrayAdapter
                val feedAdapter =
                    FeedAdapter(mContext, R.layout.list_record, ParseApplications.applications)
                mListView.adapter = feedAdapter
            }
        }

        private fun downloadXML(urlPath: String?): String {
            return URL(urlPath).readText()

        }
    }
}
