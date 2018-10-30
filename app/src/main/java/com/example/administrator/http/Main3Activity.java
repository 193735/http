package com.example.administrator.http;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Main3Activity extends AppCompatActivity {
private static   String str;
    private static   String s;
    private static   String a;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.scroll)
    ScrollView scroll;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);


    }
    private  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            Main3Activity.this.textview.setText(str);

        }
    };
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            Main3Activity.this.textview.setText(s);
        }
    };
    private Handler mHandler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            Main3Activity.this.textview.setText(a);
        }
    };
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Request.Builder requestBuilder = new Request.Builder().url("http://blog.csdn.net/itachi85");
                requestBuilder.method("GET",null);
                final Request request = requestBuilder.build();
                OkHttpClient mOkHttpClient = new OkHttpClient();
                Call mcall = mOkHttpClient.newCall(request);
                mcall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        str = response.body().string();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                mHandler.sendEmptyMessage(0);
                            }
                        }).start();

                    }
                });
                break;
            case R.id.btn2:
                RequestBody formBody = new FormBody.Builder().add("ip","59.108.54.37").build();
                Request request1 = new Request.Builder()
                        .url("http://ip.taobao.com/service/getIpInfo.php")
                        .post(formBody)
                        .build();
                OkHttpClient OkHttpClient = new OkHttpClient();
                Call call = OkHttpClient.newCall(request1);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                                   s = response.body().string();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                mHandler1.sendEmptyMessage(0);
                            }
                        }).start();
                    }
                });
                break;
            case R.id.btn3:
                String filepath= "";
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    filepath = Environment.getExternalStorageDirectory().getAbsolutePath();

                }else {
                    return;
                }
                OkHttpClient mOkHttpClient1 = new OkHttpClient();
                File file = new File(filepath,"wangshu.txt");
                Request request2 = new Request.Builder()
                        .url("https://api.github.com/markdown/raw")
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,file))
                        .build();
                mOkHttpClient1.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        a = response.body().string();
                          new Thread(new Runnable() {
                              @Override
                              public void run() {
                                  mHandler2.sendEmptyMessage(0);
                              }
                          }).start();
                    }
                });
                break;
            case R.id.btn4:
                break;
        }
    }
}
