package com.example.administrator.http;

import android.text.TextUtils;

import org.apache.http.NameValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */

public class UrlConnManager {
    public static HttpURLConnection getHttpURLConnection(String url) {
        HttpURLConnection mHttpURLConnection = null;
        try {

            URL mUrl = new URL(url);

            mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
            mHttpURLConnection.setConnectTimeout(15000);
            mHttpURLConnection.setReadTimeout(15000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            mHttpURLConnection.setRequestProperty("User-Agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.setDoOutput(true);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return mHttpURLConnection;
    }

    public static void postParams(OutputStream outputStream, List<NameValuePair> paramsList) throws IOException {
        StringBuilder mStringBuilder = new StringBuilder();
        for (NameValuePair nameValuePair : paramsList) {
            if (!TextUtils.isEmpty(mStringBuilder)) {
                mStringBuilder.append("&");
                mStringBuilder.append(URLEncoder.encode(nameValuePair.getName(), "UTF-8"));
                mStringBuilder.append("=");
                mStringBuilder.append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
            }
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(mStringBuilder.toString());
            writer.flush();
            writer.close();
        }

    }
}