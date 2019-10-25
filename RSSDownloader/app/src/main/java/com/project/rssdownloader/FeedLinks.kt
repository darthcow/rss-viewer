/*
 *
 *  Created by Darthcow
 *  Last modified 25/10/19 12:22
 * /
 */



package com.project.rssdownloader

class FeedLinks {


    companion object{
        const val FreeApps = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml"
        const val PaidApps = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml"
        const val Songs = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml"
        const val Movies = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topMovies/xml"
    }
}