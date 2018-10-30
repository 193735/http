package com.example.administrator.http;

import android.icu.util.Output;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {
 TextView textView;
 Button button;
 EditText editText;
private static String respose;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text1);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.edit);
        String edit = editText.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        useHttpUrlConnectionPost("http://ip.taobao.com/service/getIpInfo.php");
                    }
                }).start();
            }
        });

}
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            MainActivity.this.textView.setText(respose);
        }
    };



       private void useHttpUrlConnectionPost(String url){
           try {
          url += "?ip="+"59.108.54.37";
           InputStream mInputStream = null;
           HttpURLConnection mHttpURLConnection = UrlConnManager.getHttpURLConnection(url);
//           List<NameValuePair>postParams = new ArrayList<>();
//           postParams.add(new BasicNameValuePair("ip","59.108.54.37"));
//               UrlConnManager.postParams(mHttpURLConnection.getOutputStream(),postParams);
               mHttpURLConnection.connect();
          mInputStream = mHttpURLConnection.getInputStream();
          int code = mHttpURLConnection.getResponseCode();
          respose = ConvertStreamToString(mInputStream);
               Log.d(TAG,"请求状态码:"+code+"\n请求结果：\n"+respose);
new Thread(new Runnable() {
    @Override
    public void run() {

        mHandler.sendEmptyMessage(0);
    }
}).start();

               mInputStream.close();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (Exception e) {
               e.printStackTrace();
           }

       }
    public static String ConvertStreamToString(InputStream is )
            throws Exception {
        StringBuilder sb = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(is)) {
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
            }
        }

        return sb.toString();
    }

}
