package com.example.docbao_vnexpress_basic;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class XMLParser1 {
    public String getXmlFromUrl(String url) {
        String xml = null;
        URL link = null;
        HttpURLConnection connection = null;
        try {
            link = new URL(url);
            connection = (HttpURLConnection) link.openConnection();
            BufferedReader br = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
// use a string builder to bufferize the response body
// read from the input strea.
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            xml = sb.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
// ---------------------------------------------------------------
        Log.d("HTTP-GET", xml);
        connection.disconnect();
        return xml;
    }
}
