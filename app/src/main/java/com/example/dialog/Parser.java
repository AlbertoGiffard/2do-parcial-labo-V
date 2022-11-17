package com.example.dialog;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Rss> xmlToList(String xml){
        List<Rss> rss = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();

        try {
            parser.setInput(new StringReader(xml));

            int event = parser.getEventType();
            Rss n = new Rss();

            while (event != XmlPullParser.END_DOCUMENT) {

                if(event == XmlPullParser.START_DOCUMENT){
                    Log.d("XML","Empezo el documento");
                } else if(event == XmlPullParser.START_TAG){
                    if (parser.getName().equals("title")){
                        n.titulo = parser.nextText();
                        Log.d("titulo", n.titulo);
                    } else if(parser.getName().equals("description")) {
                        n.descripcion = parser.nextText();
                    } else if(parser.getName().equals("enclosure")) {
                        n.urlImg = "https:" + parser.getAttributeValue(null, "url");
                    }
                } else if(event == XmlPullParser.END_TAG){
                    if (parser.getName().equals("item")){
                        rss.add(n);
                        n = new Rss();
                    }
                }

                event = parser.next();
            }
            Log.d("XML","termino el documento");

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


        return  rss;
    }
}
