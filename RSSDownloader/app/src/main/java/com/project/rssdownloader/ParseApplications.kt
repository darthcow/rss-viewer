/*
 *
 *  Created by Darthcow
 *  Last modified 25/10/19 12:22
 * /
 */



package com.project.rssdownloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.util.*



class ParseApplications {

        private val TAG = "ParseApplications"
        val applications = ArrayList<FeedEntry>()
    fun parse(xmlData: String): Boolean {
//        Log.d(TAG, "parse called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName =
                    xpp.name?.toLowerCase(Locale.getDefault())    // TODO: we should use the safe-call operator ?
                when (eventType) {

                    XmlPullParser.START_TAG -> {
//                        Log.d(TAG, "parse: Starting tag for $tagName")
                        if (tagName == "entry") {
                            inEntry = true
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
//                        Log.d(TAG, "parse: Ending tag for $tagName")
                        if (inEntry) {
                            when (tagName) {
                                "entry" -> {
                                    applications.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntry()   // create a new object
                                }
                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "im:releaseDate" -> currentRecord.releaseDate = textValue
                                "summary" -> currentRecord.summary = textValue
                                "image" -> currentRecord.imageURL = textValue
                            }
//                           Log.d(TAG, currentRecord.toString())
                        }
                    }
                }

                // Nothing else to do.
                eventType = xpp.next()
            }


        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}
