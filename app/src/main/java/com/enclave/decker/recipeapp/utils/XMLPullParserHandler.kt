package com.enclave.decker.recipeapp.utils

import com.enclave.decker.recipeapp.model.RecipeType
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class XMLPullParserHandler {

    private val recipeTypes: MutableList<RecipeType>

    private var recipeType: RecipeType? = null

    private var text: String? = null


    init {
        recipeTypes = ArrayList()
    }

    fun parse(inputStream: InputStream): List<RecipeType> {
        val factory: XmlPullParserFactory?
        val parser: XmlPullParser?
        try {
            factory = XmlPullParserFactory.newInstance()
            factory!!.isNamespaceAware = true
            parser = factory.newPullParser()
            parser!!.setInput(inputStream, null)
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagname = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> if (tagname.equals(
                            "recipetype",
                            ignoreCase = true
                        )
                    ) {
                        recipeType = RecipeType()
                    }
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> if (tagname.equals("recipetype", ignoreCase = true)) {
                        recipeTypes.add(recipeType!!)
                    } else if (tagname.equals("name", ignoreCase = true)) {
                        recipeType!!.name =text!!
                    }
                    else -> {
                    }
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return recipeTypes
    }
}