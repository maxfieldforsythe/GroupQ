package com.csci448.qquality.groupq.data

/*
* This class is currently intended to hold the data for songs in a search query.
*   Depending on how the rest of the app is implemented, it may be useful to
*  rename the class and use one song (data?) class in all uses of song, but
*  for now it is called SongSearchResult because that si where it is being used
 */
data class SongSearchResult(var title: String, var urlEnding: String)