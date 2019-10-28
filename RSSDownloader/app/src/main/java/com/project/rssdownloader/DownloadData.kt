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

private class DownloadData(private val callBack: DownloaderCallBack) :
    AsyncTask<String, Void, String>() {
    var mContext: Context by Delegates.notNull()
    var mListView: ListView by Delegates.notNull()

    interface DownloaderCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }



    override fun doInBackground(vararg p0: String?): String {
        Log.d("", "doInBackground called starts with ${p0[0]}")
        val feed = downloadXML(p0[0])
        if (feed.isEmpty()) {
            Log.d("", "error downloading feed")
        }
        return feed
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        ParseApplications.parse(result)
        Log.d("", "onPostExecute called, parameter is $result ")

        val feedAdapter =
            FeedAdapter(mContext, R.layout.list_record, ParseApplications.applications)
        mListView.adapter = feedAdapter
    }


    private fun downloadXML(urlPath: String): String {
        return try {
            URL(urlPath).readText()
        } catch (e: Exception){
            "Error parsing XML:${e.message}"
        }


    }
}