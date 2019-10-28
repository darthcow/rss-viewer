/*
 *
 *  Created by Darthcow
 *  Last modified 28/10/19 10:45
 * /
 */

package com.project.rssdownloader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

val EMPTY_FEED_LIST: List<FeedEntry> = emptyList()

class FeedViewModel : ViewModel(), DownloadData.DownloaderCallBack {

    private var downloadData: DownloadData? = null
    private var feedCacheUrl = "INVALIDATED"
    private val feed = MutableLiveData<List<FeedEntry>>()

    val feedEntries: LiveData<List<FeedEntry>>
        get() = feed

    init {
        feed.postValue(EMPTY_FEED_LIST)
    }

    override fun onDataAvailable(data: List<FeedEntry>) {
        feed.value = data
    }

    fun downloadURL(url: String) {
        downloadData = DownloadData(this)
        downloadData?.execute(url)
//        feedCacheUrl = url
    }


    override fun onCleared() {
        downloadData?.cancel(true)
        super.onCleared()

    }

    fun invalidate(){
        feedCacheUrl = "INVALIDATED"
    }
}