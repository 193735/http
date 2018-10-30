package com.example.administrator.http;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.textview)
    TextView textview;
    private String TAG = ".Main2Activity";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:

                StringRequest StringRequest = new StringRequest(Request.Method.GET, "https://www.baidu.com",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Log.d(TAG, s);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage(), volleyError);
                    }
                }
                );
                mQueue.add(StringRequest);

                break;
            case R.id.btn2:

                JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://ip.taobao.com/service/getIpInfo.php?ip=59.108.54.37",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                IpModel ipModel = new Gson().fromJson(response.toString(), IpModel.class);
                                if (null != ipModel && null != ipModel.getData()) {
                                    String city = ipModel.getData().getCity();
                                    Log.d(TAG, city);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
                }
                );
                mQueue.add(mJsonObjectRequest);
                break;
            case R.id.btn3:

                break;
        }
    }
}
