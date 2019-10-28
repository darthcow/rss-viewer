/*
 *
 *  Created by Darthcow
 *  Last modified 25/10/19 12:29
 * /
 */

package com.project.rssdownloader

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import java.lang.Exception
import java.net.URL
import kotlin.properties.Delegates

class DownloadData(private val callBack: DownloaderCallBack) :
    AsyncTask<String, Void, String>() {
    var mContext: Context by Delegates.notNull()
    var mListView: ListView by Delegates.notNull()

    interface DownloaderCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }

    override fun doInBackground(vararg p0: String?): String {
        val feed = downloadXML(p0[0])
        if (feed.isEmpty()) {
            Log.d("", "error downloading feed")
        }
        return feed
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        val parseApplications = ParseApplications()
        if (result.isNotEmpty())
            parseApplications.parse(result)

        callBack.onDataAvailable(parseApplications.applications)
//        val feedAdapter = FeedAdapter(mContext, R.layout.list_record, parseApplications.applications)
//        mListView.adapter = feedAdapter
    }


    private fun downloadXML(urlPath: String?): String {
        return try {
            URL(urlPath).readText()
        } catch (e: Exception) {
            "Error parsing XML:${e.message}"
        }


    }
}
