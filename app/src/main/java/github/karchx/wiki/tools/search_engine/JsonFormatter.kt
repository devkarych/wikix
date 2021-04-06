package github.karchx.wiki.tools.search_engine

import org.json.JSONObject
import kotlin.collections.ArrayList

class JsonFormatter {

    private val unicodeFormatter: UnicodeFormatter = UnicodeFormatter()

    fun listOfPages(jsonInp: String): ArrayList<ArrayList<String>> {
        val pagesData: ArrayList<ArrayList<String>> = arrayListOf()

        // decode json to "normal" style
        val jsonStr = unicodeFormatter.decode(jsonInp)
        val obj = JSONObject(jsonStr)
        val arrOfPages = obj.getJSONArray("search")

        // get Pages info (will be shown in recycler)
        for (i in 0 until arrOfPages.length()) {
            val pageUrl = arrOfPages.getJSONObject(i).getString("url")
            val pageLabel = arrOfPages.getJSONObject(i).getString("label")
            val pageDesc = arrOfPages.getJSONObject(i).getString("description")

            pagesData.add(arrayListOf(pageUrl, pageLabel, pageDesc))
        }

        return pagesData
    }
}