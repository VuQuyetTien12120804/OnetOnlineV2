package com.example.docbao_vnexpress_basic;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    String url = "https://vnexpress.net/rss/tin-noi-bat.rss";
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);
        LoadExampleTask task = new LoadExampleTask();
        task.execute();

    }

    class LoadExampleTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mylist.clear();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            myadapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parser = fc.newPullParser();

                XMLParser1 myparser = new XMLParser1();
                String xml = myparser.getXmlFromUrl(url);

                parser.setInput(new StringReader(xml));

                int eventType = -1;
                String nodeName = "";
                while(eventType != XmlPullParser.END_DOCUMENT){
                    eventType = parser.next();
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT: break;
                        case XmlPullParser.END_DOCUMENT: break;
                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("title")){
                                title = parser.nextText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")){
                                mylist.add(title);
                            }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}